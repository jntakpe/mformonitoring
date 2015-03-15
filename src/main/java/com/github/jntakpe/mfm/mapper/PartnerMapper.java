package com.github.jntakpe.mfm.mapper;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.dto.HealthDTO;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mappings associés aux {@link com.github.jntakpe.mfm.domain.Partner}
 *
 * @author jntakpe
 */
public final class PartnerMapper {

    private PartnerMapper() {
    }

    /**
     * Transforme un {@link HealthDTO} en set de partenaires
     *
     * @param health      bean wrappant le résultat d'une url de vie d'une application
     * @param application application
     * @return set de partenaires
     */
    public static Set<Partner> toSet(HealthDTO health, Application application) {
        Set<Partner> partners = new HashSet<>();
        if (health.getSelClientHealth() != null) {
            partners.add(health.getSelCrmHealth());
        }
        if (health.getSelClientHealth() != null) {
            partners.add(health.getSelClientHealth());
        }
        if (health.getDatamart() != null) {
            partners.add(health.getDatamart());
        }
        return partners.stream()
                .peek(p -> p.setApplications(Collections.singleton(application)))
                .collect(Collectors.toSet());
    }
}
