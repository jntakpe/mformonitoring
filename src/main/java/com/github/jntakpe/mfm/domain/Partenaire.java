package com.github.jntakpe.mfm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * Entité représentant un partenaire
 *
 * @author jntakpe
 */
@Entity
public class Partenaire extends GenericDomain {

    private String nom;

    private String url;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partenaire that = (Partenaire) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("statut", statut)
                .append("url", url)
                .append("nom", nom)
                .toString();
    }
}
