package com.github.jntakpe.mfm.domain;

import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Bean mappand l'identifiant technique et la version
 *
 * @author jntakpe
 */
@MappedSuperclass
public abstract class GenericDomain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer lock;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
