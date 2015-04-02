package com.github.jntakpe.mfm.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import com.github.jntakpe.mfm.config.properties.WebProperties;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
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
        factory.setHttpClient(HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build());
        return new RestTemplate(factory);
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
        factory.setHttpAsyncClient(HttpAsyncClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build());
        return new AsyncRestTemplate(factory);
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

}
