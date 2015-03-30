package com.github.jntakpe.mfm.dto;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Environment;
import com.github.jntakpe.mfm.domain.Partner;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO représentant une application
 *
 * @author jntakpe
 */
public class ApplicationDTO {

    private String name;

    private String groupId;

    private String artifactId;

    private String version;

    private Environment environment;

    private String url;

    private String status;

    private Set<Partner> partners = new HashSet<>();

    public ApplicationDTO(Application application) {
        name = application.getName();
        groupId = application.getGroupId();
        artifactId = application.getArtifactId();
        version = application.getVersion();
        environment = application.getEnvironment();
        url = application.getUrl();
        status = application.getStatus();
        partners = application.getPartners();
    }

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
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
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
}