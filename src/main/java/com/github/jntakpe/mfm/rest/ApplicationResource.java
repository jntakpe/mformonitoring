package com.github.jntakpe.mfm.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Environment;
import com.github.jntakpe.mfm.domain.Status;
import com.github.jntakpe.mfm.dto.ApplicationDTO;
import com.github.jntakpe.mfm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Ressouce REST associé à une app
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.APPLICATION)
public class ApplicationResource {

    private ApplicationService applicationService;


    @Autowired
    public ApplicationResource(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * Renvoie la liste des applications
     *
     * @return la liste des applications
     */
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public List<Application> list() {
        return applicationService.findAll();
    }

    /**
     * Vérification de l'URL de monitoring. Si l'url est déjà utilisée renvoie un code d'erreur HTTP 409.
     * Si la récupération des informations échoue à l'url donnée renvoie un code d'erreur 500.
     *
     * @param url url de monitoring à tester
     * @param id  identifiant de l'app dans le cas d'une app déjà existante
     * @return la récupération des informations du projet sinon un code erreur
     */
    @Timed
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Application>> check(@RequestParam String url, @RequestParam(required = false) String id) {
        DeferredResult<ResponseEntity<Application>> deferred = new DeferredResult<>();
        Optional<Application> app = applicationService.findByUrl(url);
        if (app.isPresent() && !app.get().getId().equals(id)) {
            deferred.setResult(new ResponseEntity<>(HttpStatus.CONFLICT));
        }
        applicationService.findAppInfos(url).addCallback(
                i -> {
                    Application application = i.getBody().getApp();
                    if (application == null || application.getArtifactId() == null) {
                        deferred.setResult(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                    } else {
                        application.setUrl(url);
                        deferred.setResult(new ResponseEntity<>(application, HttpStatus.OK));
                    }
                },
                i -> deferred.setResult(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR))
        );
        return deferred;
    }

    /**
     * Ajout d'une nouvelle app
     *
     * @param application app à ajouter
     * @return app enregistrée
     */
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        application.setStatus(Status.UP);
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Récupère une app à partir de son identifiant
     *
     * @param id identifiant de l'app
     * @return l'app correspondante à l'identifiant
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Application> find(@PathVariable String id) {
        return new ResponseEntity<>(applicationService.findById(id), HttpStatus.OK);
    }

    /**
     * Modification d'une app
     *
     * @param id          identifiant de l'app
     * @param application app à modifier
     * @return app modifiée
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Suppression de l'app possédant l'id
     *
     * @param id identifiant de l'app à supprimer
     * @return code HTTP 200 si la suppression est effectuée
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        applicationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Recherche les applications en fonction de leur environnement
     *
     * @param environment environnements des applications
     * @return liste des applications recherchés
     */
    @Timed
    @RequestMapping(value = "/env/{environment}", method = RequestMethod.GET)
    public ResponseEntity<List<ApplicationDTO>> findByEnvironment(@PathVariable Environment environment) {
        List<ApplicationDTO> apps = applicationService.findByEnvironment(environment).stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

}
