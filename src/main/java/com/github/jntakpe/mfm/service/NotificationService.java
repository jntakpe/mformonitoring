package com.github.jntakpe.mfm.service;

import com.github.jntakpe.mfm.domain.Notification;
import com.github.jntakpe.mfm.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services associés à l'entité {@link com.github.jntakpe.mfm.domain.Notification}
 *
 * @author jntakpe
 */
@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Création d'une nouvelle {@link Notification}
     *
     * @param notification notification a enregistrer
     * @return la notification enregistrée
     */
    public Notification create(Notification notification) {
        LOG.info("Enregistrement de la notification {}", notification);
        return notificationRepository.save(notification);
    }

    /**
     * Recherche de toutes les notifications
     *
     * @return la liste de toutes les notifications
     */
    public List<Notification> findAll() {
        LOG.debug("Récupération de la liste des notifications");
        return notificationRepository.findAll();
    }
}
