package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * Vérification de l'URL de monitoring
     *
     * @param url url de monitoring à tester
     * @return récupération des informations du projet
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Application> check(@RequestParam String url) {
        return new ResponseEntity<>(applicationService.findAppInfos(url), HttpStatus.OK);
    }

}
