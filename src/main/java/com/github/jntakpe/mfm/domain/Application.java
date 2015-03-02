package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * Entité représentant une application
 *
 * @author jntakpe
 */
@Entity
public class Application extends GenericDomain {

    private String nom;

    private String groupId;

    private String artifactId;

    private String version;

    private Environnement environnement;

    private boolean active;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(nom, that.nom) &&
                Objects.equals(environnement, that.environnement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, environnement);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nom", nom)
                .append("groupId", groupId)
                .append("artifactId", artifactId)
                .append("version", version)
                .append("environnement", environnement)
                .append("active", active)
                .toString();
    }
}
