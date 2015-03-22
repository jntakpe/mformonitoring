package com.github.jntakpe.mfm.service;

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
        return restTemplate.getForObject(url, String.class);
    }
}
