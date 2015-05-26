package com.github.jntakpe.mfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface publiant les méthodes génériques de gestion des entités persistés via Spring Data JPA
 *
 * @author jntakpe
 */
@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, String> {

}
