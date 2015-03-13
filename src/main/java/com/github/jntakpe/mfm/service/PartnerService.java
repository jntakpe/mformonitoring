package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.dto.HealthDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * Services associés à l'entité {@link Partner}
 *
 * @author jntakpe
 */
@Service
public class PartnerService {

    private static final Logger LOG = LoggerFactory.getLogger(PartnerService.class);

    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    public PartnerService(AsyncRestTemplate asyncRestTemplate) {
        this.asyncRestTemplate = asyncRestTemplate;
    }

    /**
     * Récupération des informations de health check pour une url
     *
     * @param url url de l'application
     * @return callback sur les informations du health check
     */
    public ListenableFuture<ResponseEntity<HealthDTO>> health(String url) {
        LOG.debug("Recherche des statuts partenaires pour l'URL {}", url);
        return asyncRestTemplate.getForEntity(url, HealthDTO.class);
    }

}
