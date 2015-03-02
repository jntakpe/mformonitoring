package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
@Service
public class ApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

    private RestTemplate restTemplate;

    @Autowired
    public ApplicationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupère les informations d'une application à l'aide de l'url de monitoring
     *
     * @param uri url de monitoring du projet
     * @return renvoie une {@link org.springframework.http.ResponseEntity} contenant les infos du projet
     */
    public ResponseEntity<Application> findAppInfos(URI uri) {
        LOG.debug("Tentative de récupération des informations pour à l'uri {}", uri);
        return restTemplate.getForEntity(uri, Application.class);
    }
}
