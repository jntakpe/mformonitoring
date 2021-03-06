package com.github.jntakpe.mfm.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.mfm.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ressource associée aux métriques
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.METRICS)
public class MetricsResource {

    private MetricsService metricsService;

    @Autowired
    public MetricsResource(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    /**
     * Recherche de métriques à l'url donnée
     *
     * @param url url des métriques
     * @return JSON de métriques
     */
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> find(@RequestParam String url) {
        return new ResponseEntity<>(metricsService.findMetrics(url), HttpStatus.OK);
    }
}
