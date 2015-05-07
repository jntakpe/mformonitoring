package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.dto.LoggerDTO;
import com.github.jntakpe.mfm.service.ApplicationService;
import com.github.jntakpe.mfm.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Resource associée aux loggers
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.LOGS)
public class LogsResource {

    private LogsService logsService;

    private ApplicationService applicationService;

    @Autowired
    public LogsResource(LogsService logsService, ApplicationService applicationService) {
        this.logsService = logsService;
        this.applicationService = applicationService;
    }

    /**
     * Renvoie la liste des loggers
     *
     * @return liste des loggers
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String list(@PathVariable String id) {
        Application app = applicationService.findById(id);
        return logsService.findAll(logsService.toLogsUri(app.getUrl()));
    }

    /**
     * Modifie le niveau d'un logger
     *
     * @param logger bean représentant un logger
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLevel(@PathVariable String id, @RequestBody LoggerDTO logger) {
        Application app = applicationService.findById(id);
        logsService.update(logsService.toLogsUri(app.getUrl()), logger);
    }

}
