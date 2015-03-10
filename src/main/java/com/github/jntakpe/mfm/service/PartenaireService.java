package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.repository.PartenaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mfm.domain.Partenaire}
 *
 * @author jntakpe
 */
@Service
public class PartenaireService {

    private PartenaireRepository partenaireRepository;

    private ApplicationService applicationService;

    @Autowired
    public PartenaireService(PartenaireRepository partenaireRepository, ApplicationService applicationService) {
        this.partenaireRepository = partenaireRepository;
        this.applicationService = applicationService;
    }


}
