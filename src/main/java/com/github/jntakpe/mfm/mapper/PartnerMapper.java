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

    public static Set<Partner> toSet(HealthDTO health, Application application) {
        HashSet<Partner> partners = new HashSet<>();
        partners.add(health.getSelCrmHealth());
        partners.add(health.getSelClientHealth());
        return partners.stream()
                .peek(p -> p.setApplications(Collections.singleton(application)))
                .collect(Collectors.toSet());
    }
}
