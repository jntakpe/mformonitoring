package com.github.jntakpe.mfm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Services associés aux métriques
 *
 * @author jntakpe
 */
@Service
public class MetricsService {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsService.class);

    private RestTemplate restTemplate;

    @Autowired
    public MetricsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupération des métriques à l'url donnée
     *
     * @param url url des métriques
     * @return JSON brut de métriques
     */
    public String findMetrics(String url) {
        LOG.debug("Récupération des métriques à l'url {}", url);
        return restTemplate.getForObject(url, String.class);
    }
}
