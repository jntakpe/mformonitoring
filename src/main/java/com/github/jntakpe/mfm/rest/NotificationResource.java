package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.domain.Notification;
import com.github.jntakpe.mfm.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ressource REST gérant les notifications
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.NOTIF)
public class NotificationResource {

    private NotificationService notificationService;

    @Autowired
    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Récupération des notifications
     *
     * @return liste des notifications
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Notification> findAll() {
        return notificationService.findAll();
    }
}
