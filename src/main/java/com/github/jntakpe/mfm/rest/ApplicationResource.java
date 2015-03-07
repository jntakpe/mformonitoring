package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(method = RequestMethod.GET)
    public List<Application> list() {
        return applicationService.findAll();
    }

    /**
     * Vérification de l'URL de monitoring. Si l'url est déjà utilisée renvoie un code d'erreur HTTP 409.
     * Si la récupération des informations échoue à l'url donnée renvoie un code d'erreur 500.
     *
     * @param url url de monitoring à tester
     * @return la récupération des informations du projet sinon un code erreur
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Application> check(@RequestParam String url) {
        if (applicationService.findByUrl(url).isPresent()) {
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }
}
