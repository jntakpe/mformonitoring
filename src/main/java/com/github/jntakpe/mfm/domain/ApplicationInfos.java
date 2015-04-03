package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Bean wrappant les informations nécessaires d'une application pour les notifications
 *
 * @author jntakpe
 */
public class ApplicationInfos {

    private String id;

    private String name;

    private String environment;

    private String originVersion;

    private String destVersion;

    public ApplicationInfos() {
    }

    public ApplicationInfos(Application application) {
        id = application.getId();
        name = application.getName();
        environment = application.getEnvironment().name();
    }

    public ApplicationInfos(Application origin, Application dest) {
        this(origin);
        originVersion = origin.getVersion();
        destVersion = dest.getVersion();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getOriginVersion() {
        return originVersion;
    }

    public void setOriginVersion(String originVersion) {
        this.originVersion = originVersion;
    }

    public String getDestVersion() {
        return destVersion;
    }

    public void setDestVersion(String destVersion) {
        this.destVersion = destVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("environment", environment)
                .toString();
    }
}
