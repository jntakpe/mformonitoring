package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
@Service
public class ApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

    private RestTemplate restTemplate;

    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(RestTemplate restTemplate, ApplicationRepository applicationRepository) {
        this.restTemplate = restTemplate;
        this.applicationRepository = applicationRepository;
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

    /**
     * Récupération de la liste des applications
     *
     * @return liste des applications
     */
    @Transactional(readOnly = true)
    public List<Application> findAll() {
        LOG.debug("Récupération de la liste des applications");
        return applicationRepository.findAll();
    }


}
