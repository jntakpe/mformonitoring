package com.github.jntakpe.mfm.mapper;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.domain.Status;
import com.github.jntakpe.mfm.dto.HealthDTO;

import java.util.Set;

/**
 * Mappings associés au bean {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
public final class ApplicationMapper {

    private ApplicationMapper() {
    }

    /**
     * Map un {@link HealthDTO} vers une {@link Application}
     *
     * @param healthDTO   résultat du fil de vie
     * @param application application initiale
     * @return application mappée avec le fil de vie
     */
    public static Application map(HealthDTO healthDTO, Application application) {
        application.setStatus(Status.valueOf(healthDTO.getStatus()));
        Set<Partner> partners = application.getPartners();
        partners.add(healthDTO.getSelClientHealth());
        partners.add(healthDTO.getSelCrmHealth());
        return application;
    }
}
