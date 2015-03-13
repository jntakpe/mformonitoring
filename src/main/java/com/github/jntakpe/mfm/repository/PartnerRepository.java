package com.github.jntakpe.mfm.repository;

import com.github.jntakpe.mfm.domain.Partner;

import java.util.Optional;

/**
 * Publication des méthodes de gestion de l'entité {@link Partner}
 *
 * @author jntakpe
 */
public interface PartnerRepository extends GenericRepository<Partner> {

    Optional<Partner> findByUrl(String url);
}
