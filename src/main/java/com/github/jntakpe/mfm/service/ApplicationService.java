package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Environment;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Application save(Application application) {
        LOG.info("Enregistrement de l'application {}", application);
        return applicationRepository.save(application);
    }

    /**
     * Suppression de l'application possédant cet identifiant
     *
     * @param id identifiant de l'application à supprimer
     */
    public void delete(String id) {
        Application app = findById(id);
        LOG.info("Suppression de l'application", app);
        app.getPartners().stream().forEach(p -> p.removeApplication(app));
        applicationRepository.delete(app);
    }

    /**
     * Récupère une application en fonction de son identifiant
     *
     * @param id identifiant de l'application
     * @return l'application correspondante à l'identifiant
     */
    public Application findById(String id) {
        LOG.debug("Recherche de l'application id {}", id);
        return applicationRepository.findOne(id);
    }

    /**
     * Récupère une application en fonction de son identifiant avec les partenaires associés
     *
     * @param id identifiant de l'application
     * @return l'application correspondante à l'identifiant et les partenaires associés
     */
    public Application findByIdWithPartners(String id) {
        return findById(id);
    }

    /**
     * Récupère la liste des applications en fonction d'un environnement
     *
     * @param environment environnement des applications
     * @return la liste des applications correspondantes à l'environnement
     */
    public List<Application> findByEnvironment(Environment environment) {
        LOG.debug("Recherche des applications de l'environnement {}");
        return applicationRepository.findByEnvironment(environment);
    }

    /**
     * Récupère la liste des applications d'un partenaire et les enregistre
     *
     * @param partner partenaire contenant la liste des applications
     * @return la liste des applications enregistrées
     */
    public Set<Application> save(Partner partner) {
        return partner.getApplications().stream()
                .filter(a -> a.getId() != null)
                .peek(a -> a.addPartner(partner))
                .peek(this::save)
                .collect(Collectors.toSet());
    }
}
