package com.github.jntakpe.mfm.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Ressouce REST associé à une application
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
     * @param id  identifiant de l'application dans le cas d'une application déjà existante
     * @return la récupération des informations du projet sinon un code erreur
     */
    @Timed
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Application> check(@RequestParam String url, @RequestParam(required = false) Long id) {
        Optional<Application> app = applicationService.findByUrl(url);
        if (app.isPresent() && !app.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(applicationService.findAppInfos(url), HttpStatus.OK);
    }

    /**
     * Ajout d'une nouvelle application
     *
     * @param application application à ajouter
     * @return application enregistrée
     */
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Récupère une application à partir de son identifiant
     *
     * @param id identifiant de l'application
     * @return l'application correspondante à l'identifiant
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Application> find(@PathVariable String id) {
        return new ResponseEntity<>(applicationService.findById(id), HttpStatus.OK);
    }

    /**
     * Modification d'une application
     *
     * @param id          identifiant de l'application
     * @param application application à modifier
     * @return application modifiée
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Suppression de l'application possédant l'id
     *
     * @param id identifiant de l'application à supprimer
     * @return code HTTP 200 si la suppression est effectuée
     */
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) {
        applicationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
