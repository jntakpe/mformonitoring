package com.github.jntakpe.mfm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Services associés aux paramétrage
 *
 * @author jntakpe
 */
@Service
public class ParameterService {

    private static final Logger LOG = LoggerFactory.getLogger(ParameterService.class);

    private RestTemplate restTemplate;

    @Autowired
    public ParameterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupération des paramètres
     *
     * @param url url des paramètres
     * @return JSON brut de paramétrage
     */
    public String findParameters(String url) {
        LOG.debug("Récupération des propriétés à l'url {}", url);
        return restTemplate.getForObject(url, String.class);
    }
}
