package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.dto.HealthDTO;
import com.github.jntakpe.mfm.repository.PartnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.Optional;

/**
 * Services associés à l'entité {@link Partner}
 *
 * @author jntakpe
 */
@Service
public class PartnerService {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerService.class);

    private AsyncRestTemplate asyncRestTemplate;

    private PartnerRepository partnerRepository;

    @Autowired
    public PartnerService(AsyncRestTemplate asyncRestTemplate, PartnerRepository partnerRepository) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.partnerRepository = partnerRepository;
    }

    /**
     * Récupération des informations de health check pour une url
     *
     * @param url url de l'app
     * @return callback sur les informations du health check
     */
    public ListenableFuture<ResponseEntity<HealthDTO>> health(String url) {
        LOG.debug("Recherche des statuts partenaires pour l'URL {}", url);
        return asyncRestTemplate.getForEntity(url, HealthDTO.class);
    }

    /**
     * Enregistre un partenaire en vérifiant dans un premier temps si un partenaire avec cette url n'existe pas déjà
     *
     * @param partner partenaire à enregistrer
     * @return partenaire enregistré
     */
    public Partner save(Partner partner) {
        LOG.info("Enregistrement du partenaire {}", partner);
        Optional<Partner> opt = partnerRepository.findByUrl(partner.getUrl());
        if (partner.getId() == null && !opt.isPresent()) {
            return partnerRepository.save(partner);
        }
        Partner existing = opt.get();
        return existing.isSame(partner) ? existing : partnerRepository.save(partner);
    }

}
