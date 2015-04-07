package com.github.jntakpe.mfm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entité représentant une app
 *
 * @author jntakpe
 */
@Document
public class Application extends GenericDomain {

    @NotNull
    private String name;

    private String artifactId;

    private String version;

    private String environment = Environment.UNKNOWN.name();

    @NotNull
    @Indexed(unique = true)
    private String url;

    private String status = Status.UNKNOWN.name();

    @JsonIgnore
    @DBRef(lazy = true)
    private Set<Partner> partners = new HashSet<>();

    public String healthUrl() {
        return StringUtils.removeEnd(url, "info") + "health";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.name();
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

                .append("artifactId", artifactId)
                .append("version", version)
                .append("environment", environment)
                .append("url", url)
                .append("status", status)
                .toString();
    }
}
