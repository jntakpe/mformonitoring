package com.github.jntakpe.mfm.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Propriétés associées au proxy
 *
 * @author ntakpe_j
 */
@Component
@ConfigurationProperties("proxy")
public class ProxyProperties {

    private String url;

    private Integer port;

    private String user;

    private String password;

    private String workstation;

    private String domain;

    private List<String> byPassProxy;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getByPassProxy() {
        return byPassProxy;
    }

    public void setByPassProxy(List<String> byPassProxy) {
        this.byPassProxy = byPassProxy;
    }
}
