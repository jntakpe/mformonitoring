package com.github.jntakpe.mfm.repository;

import com.github.jntakpe.mfm.domain.Application;

import java.util.Optional;

/**
 * Publication des méthodes de gestion de l'entité {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
public interface ApplicationRepository extends GenericRepository<Application> {

    Optional<Application> findByUrl(String url);

}
