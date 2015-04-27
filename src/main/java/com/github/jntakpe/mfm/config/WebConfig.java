package com.github.jntakpe.mfm.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import com.github.jntakpe.mfm.config.properties.ProxyProperties;
import com.github.jntakpe.mfm.config.properties.WebProperties;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

/**
 * Configuration du contexte Web
 *
 * @author jntakpe
 */
@Configuration
public class WebConfig implements ServletContextInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private WebProperties webProperties;

    @Autowired
    private ProxyProperties proxyProperties;

    /**
     * Template permettant d'accéder aux ressources REST
     *
     * @return le template configuré
     */
    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(webProperties.getTimeout());
        factory.setReadTimeout(webProperties.getTimeout());
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        httpClientBuilder.setProxy(proxy());
        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider());
        httpClientBuilder.setRoutePlanner(httpRoutePlanner());
        factory.setHttpClient(httpClientBuilder.build());
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(responseErrorHandler());
        return restTemplate;
    }

    /**
     * Template permettant d'accéder aux ressources REST de manière asynchrone et non bloquante
     *
     * @return le template asynchrone configuré
     */
    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        HttpComponentsAsyncClientHttpRequestFactory factory = new HttpComponentsAsyncClientHttpRequestFactory();
        factory.setConnectTimeout(webProperties.getTimeout());
        factory.setReadTimeout(webProperties.getTimeout());
        HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClients.custom();
        httpAsyncClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier());
        httpAsyncClientBuilder.setProxy(proxy());
        httpAsyncClientBuilder.setRoutePlanner(httpRoutePlanner());
        httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider());
        factory.setHttpAsyncClient(httpAsyncClientBuilder.build());
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(factory);
        asyncRestTemplate.setErrorHandler(responseErrorHandler());
        return asyncRestTemplate;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LOG.debug("Configuration du context WEB");
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
        initMetrics(servletContext, disps);
    }

    private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
        LOG.debug("Initialisation des registres Metrics");
        servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE, metricRegistry);
        servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY, metricRegistry);
        LOG.debug("Ajout du filtre Metrics");
        Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter", new InstrumentedFilter());
        metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
        metricsFilter.setAsyncSupported(true);
        LOG.debug("Ajout de la servlet Metrics");
        ServletRegistration.Dynamic metricsAdminServlet = servletContext.addServlet("metricsServlet", new MetricsServlet());
        metricsAdminServlet.addMapping("/manage/metrics/*");
        metricsAdminServlet.setAsyncSupported(true);
        metricsAdminServlet.setLoadOnStartup(2);
    }

    private ResponseErrorHandler responseErrorHandler() {
        return new DefaultResponseErrorHandler() {
            @Override
            protected boolean hasError(HttpStatus statusCode) {
                return statusCode != HttpStatus.SERVICE_UNAVAILABLE && super.hasError(statusCode);
            }
        };
    }

    private HttpHost proxy() {
        return new HttpHost(proxyProperties.getUrl(), proxyProperties.getPort());
    }

    private HttpRoutePlanner httpRoutePlanner() {
        return new DefaultProxyRoutePlanner(proxy()) {
            @Override
            public HttpRoute determineRoute(HttpHost host, HttpRequest request, HttpContext context) throws HttpException {
                String hostname = host.getHostName();
                if (proxyProperties.getByPassProxy().stream().filter(u -> u.contains(hostname)).findAny().isPresent()) {
                    return new HttpRoute(host);
                }
                return super.determineRoute(host, request, context);
            }
        };
    }

    private CredentialsProvider credentialsProvider() {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope(proxyProperties.getUrl(), proxyProperties.getPort()),
                new NTCredentials(proxyProperties.getUser(), proxyProperties.getPassword(),
                        proxyProperties.getWorkstation(), proxyProperties.getDomain())
        );
        return credentialsProvider;
    }

}
