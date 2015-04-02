package com.github.jntakpe.mfm.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.mfm.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Resource REST associé aux informations de paramétrage et d'environnement d'une app
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.PARAM)
public class ParameterResource {

    private ParameterService parameterService;

    @Autowired
    public ParameterResource(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    /**
     * Recherche des paramètres de l'app
     *
     * @param url url des paramètres
     * @return JSON des paramètres
     */
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> find(@RequestParam String url) {
        return new ResponseEntity<>(parameterService.findParameters(url), HttpStatus.OK);
    }
}
