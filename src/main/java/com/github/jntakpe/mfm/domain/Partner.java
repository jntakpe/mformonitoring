package com.github.jntakpe.mfm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un partenaire
 *
 * @author jntakpe
 */
@Document
public class Partner extends GenericDomain {

    private String name;

    private String url;

    private String status;

    @DBRef(lazy = true)
    @JsonIgnoreProperties("partners")
    private Set<Application> applications = new HashSet<>();

    /**
     * Indique si le partenaire a été modifié (cad chgt de statut)
     *
     * @param partner partenaire a tester
     * @return true si les partenaires sont identiques
     */
    public boolean isSame(Partner partner) {
        return this.equals(partner) && status.equals(partner.status);
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
        return Status.valueOf(status);
    }

    public void setStatus(Status status) {
        this.status = status.name();
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public void removeApplication(Application application) {
        applications.remove(application);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partner partner = (Partner) o;

        return !(name != null ? !name.equals(partner.name) : partner.name != null) && !(url != null ? !url.equals(partner.url) : partner.url != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("url", url)
                .append("status", status)
                .toString();
    }
}
