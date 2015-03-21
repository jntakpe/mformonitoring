package com.github.jntakpe.mfm.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;

/**
 * Bean mappand l'identifiant technique et la version
 *
 * @author jntakpe
 */
public abstract class GenericDomain implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String lastModifiedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
