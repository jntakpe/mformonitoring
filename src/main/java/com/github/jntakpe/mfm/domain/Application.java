package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entité représentant une application
 *
 * @author jntakpe
 */
@Document
public class Application extends GenericDomain {

    @NotNull
    private String name;

    private String groupId;

    private String artifactId;

    private String version;

    private String environment;

    private boolean active;

    @NotNull
    @Indexed(unique = true)
    private String url;

    private String status;

    @DBRef(lazy = true)
    private Set<Partner> partners = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Environment getEnvironment() {
        return Environment.valueOf(environment);
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment.name();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
    }

    public void addPartner(Partner partner) {
        partners.add(partner);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("environment", environment)
                .append("active", active)
                .append("url", url)
                .toString();
    }
}
