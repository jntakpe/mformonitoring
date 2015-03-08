package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
@Service
public class ApplicationService {

    public static final String APPS_CACHE = "apps";

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
     * @param url url de monitoring du projet
     * @return renvoie les informations du projet
     */
    public Application findAppInfos(String url) {
        LOG.debug("Tentative de récupération des informations pour à l'uri {}", url);
        Application application = restTemplate.getForObject(url, Application.class);
        application.setUrl(url);
        return application;
    }

    /**
     * Récupération de la liste des applications
     *
     * @return liste des applications
     */
    @Cacheable(value = APPS_CACHE)
    @Transactional(readOnly = true)
    public List<Application> findAll() {
        LOG.debug("Récupération de la liste des applications");
        return applicationRepository.findAll();
    }

    /**
     * Recherche d'une application correspondante à l'url
     *
     * @param url url de l'application
     * @return l'application correspondante à l'url
     */
    @Transactional(readOnly = true)
    public Optional<Application> findByUrl(String url) {
        LOG.debug("Recherche d'une application possédant url {}", url);
        return applicationRepository.findByUrl(url);
    }

    /**
     * Enregistrement de l'application
     *
     * @param application application à enregistrer
     * @return application enregistrée
     */
    @Transactional
    @CacheEvict(value = APPS_CACHE, allEntries = true)
    public Application save(Application application) {
        LOG.info("Enregistrement de l'application {}", application);
        return applicationRepository.save(application);
    }

    /**
     * Suppression de l'application possédant cet identifiant
     *
     * @param id identifiant de l'application à supprimer
     */
    @Transactional
    @CacheEvict(value = APPS_CACHE, allEntries = true)
    public void delete(Long id) {
        LOG.info("Suppression de l'application id {}", id);
        applicationRepository.delete(id);
    }
}
