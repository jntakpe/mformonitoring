package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entité représentant un partenaire
 *
 * @author jntakpe
 */
@Entity
public class Partner extends GenericDomain {

    private String name;

    @Column(unique = true)
    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(name = "partner_application", joinColumns = {@JoinColumn(referencedColumnName = "id", name = "partner_id")},
            inverseJoinColumns = {@JoinColumn(referencedColumnName = "id", name = "application_id")})
    private Set<Application> applications = new HashSet<>();

    /**
     * Indique si le partenaire a été modifié (cad chgt de statut)
     *
     * @param partner partenaire a tester
     * @return true si les partenaires sont identiques
     */
    public boolean isSame(Partner partner) {
        return this.equals(partner) && status == partner.status && applications.equals(partner.applications);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner that = (Partner) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("url", url)
                .append("name", name)
                .toString();
    }
}
