package com.github.jntakpe.mfm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Interface publiant les méthodes génériques de gestion des entités persistés via Spring Data JPA
 *
 * @author jntakpe
 */
@NoRepositoryBean
public interface GenericRepository<T> extends MongoRepository<T, Long> {

}
