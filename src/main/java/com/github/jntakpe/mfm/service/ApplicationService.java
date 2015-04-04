package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.*;
import com.github.jntakpe.mfm.mapper.ApplicationMapper;
import com.github.jntakpe.mfm.repository.ApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

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

    private AsyncRestTemplate asyncRestTemplate;

    private ApplicationRepository applicationRepository;

    private NotificationService notificationService;

    @Autowired
    public ApplicationService(AsyncRestTemplate asyncRestTemplate, ApplicationRepository applicationRepository,
                              NotificationService notificationService) {
        this.asyncRestTemplate = asyncRestTemplate;
        this.applicationRepository = applicationRepository;
        this.notificationService = notificationService;
    }

    /**
     * Récupère les informations d'une app à l'aide de l'url de monitoring
     *
     * @param url url de monitoring du projet
     * @return renvoie les informations du projet
     */
    public ListenableFuture<ResponseEntity<Informations>> findAppInfos(String url) {
        LOG.debug("Tentative de récupération des informations pour à l'uri {}", url);
        return asyncRestTemplate.getForEntity(url, Informations.class);
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
     * Recherche d'une app correspondante à l'url
     *
     * @param url url de l'app
     * @return l'app correspondante à l'url
     */
    public Optional<Application> findByUrl(String url) {
        LOG.debug("Recherche d'une application possédant url {}", url);
        return applicationRepository.findByUrl(url);
    }

    /**
     * Enregistrement de l'app
     *
     * @param application app à enregistrer
     * @return app enregistrée
     */
    public Application save(Application application) {
        LOG.info("Enregistrement de l'application {}", application);
        return applicationRepository.save(application);
    }

    /**
     * Suppression de l'app possédant cet identifiant
     *
     * @param id identifiant de l'app à supprimer
     */
    public void delete(String id) {
        Application app = findById(id);
        LOG.info("Suppression de l'application", app);
        app.getPartners().stream().forEach(p -> p.removeApplication(app));
        applicationRepository.delete(app);
    }

    /**
     * Récupère une app en fonction de son identifiant
     *
     * @param id identifiant de l'app
     * @return l'app correspondante à l'identifiant
     */
    public Application findById(String id) {
        LOG.debug("Recherche de l'application id {}", id);
        return applicationRepository.findOne(id);
    }

    /**
     * Récupère une app en fonction de son identifiant avec les partenaires associés
     *
     * @param id identifiant de l'app
     * @return l'app correspondante à l'identifiant et les partenaires associés
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
        LOG.debug("Recherche des applications de l'environnement {}", environment);
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

    /**
     * Met à jour le statut des applications et créé les notifications associées si nécessaire
     */
    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void update() {
        LOG.info("Mise à jour des statuts des applications");
        findAll().parallelStream()
                .forEach(app -> findAppInfos(app.getUrl()).addCallback(
                        a -> saveAndNotifyUp(app, a.getBody().getApp()),
                        a -> saveAndNotifyDown(app)
                ));
    }

    private void saveAndNotifyUp(Application origin, Application response) {
        if (hasStatusChanged(origin)) {
            LOG.info("Notification de démarrage de l'application {}", origin);
            notificationService.create(new Notification(origin, response, Type.START));
        }
        if (hasVersionChanged(origin, response)) {
            LOG.info("Notification de changement de version de l'application {}", origin);
            notificationService.create(new Notification(origin, response, Type.CHANGE_VERSION));
        }
        if (hasStatusChanged(origin) || hasVersionChanged(origin, response)) {
            save(ApplicationMapper.up(origin, response));
        }
    }

    private boolean hasVersionChanged(Application origin, Application response) {
        return !origin.getVersion().equals(response.getVersion());
    }

    private boolean hasStatusChanged(Application origin) {
        return origin.getStatus() != Status.UP;
    }

    private void saveAndNotifyDown(Application application) {
        if (application.getStatus() == Status.UP) {
            LOG.info("Notification d'arrêt sur l'application {}", application);
            notificationService.create(new Notification(application));
            save(ApplicationMapper.down(application));
        }
    }
}
