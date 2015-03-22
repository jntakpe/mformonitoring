package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param id identifiant de l'application
     * @param url url des métriques
     * @return JSON de métriques
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> find(@PathVariable String id, @RequestParam String url) {
        return new ResponseEntity<>(metricsService.findMetrics(url), HttpStatus.OK);
    }
}
