package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.dto.LoggerDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Services associés à la gestion des logs
 *
 * @author jntakpe
 */
@Service
public class LogsService {

    private static final Logger LOG = LoggerFactory.getLogger(LogsService.class);

    private RestTemplate restTemplate;

    @Autowired
    public LogsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Récupère tous les loggers d'une application en fonction de son url
     *
     * @param url url de l'application
     * @return tous les loggers sous forme JSON
     */
    public String findAll(String url) {
        LOG.debug("Récupération des loggers à l'url {}", url);
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * Met à jour un logger sur une application
     *
     * @param url       url de l'application
     * @param loggerDTO logger à mettre à jour
     */
    public void update(String url, LoggerDTO loggerDTO) {
        LOG.info("Mise à jour du logger {} à l'url : {}", loggerDTO, url);
        restTemplate.put(url, loggerDTO);
    }

    /**
     * Transforme une url d'info en url de logs
     *
     * @param infoUri url d'info
     * @return url de logs
     */
    public String toLogsUri(String infoUri) {
        return StringUtils.removeEnd(infoUri, "info") + "logs";
    }
}
