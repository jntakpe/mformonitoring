package com.github.jntakpe.mfm.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Propriétés associés au requêtes HTTP
 *
 * @author jntakpe
 */
@Configuration
@ConfigurationProperties("web")
public class WebProperties {

    private Integer timeout;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
