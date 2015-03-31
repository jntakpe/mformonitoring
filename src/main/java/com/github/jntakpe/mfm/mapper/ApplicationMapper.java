package com.github.jntakpe.mfm.mapper;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Status;

/**
 * Opérations de mapping associées à l'entité {@link com.github.jntakpe.mfm.domain.Application}
 *
 * @author jntakpe
 */
public final class ApplicationMapper {

    /**
     * Mise à jour d'une application existante à partir des informations récupérées
     *
     * @param origin application existante stockée en DB
     * @param infos  informations récupérées par endpoint REST
     * @return l'application existante avec les informations mises à jour
     */
    public static Application updateUp(Application origin, Application infos) {
        origin.setStatus(Status.UP);
        origin.setArtifactId(infos.getArtifactId());
        origin.setGroupId(infos.getGroupId());
        origin.setVersion(infos.getVersion());
        return origin;
    }

    /**
     * Mise à jour d'une application existante au statut DOWN
     *
     * @param origin application existante stockée en DB
     * @return l'application existante avec le statut DOWN
     */
    public static Application updateDown(Application origin) {
        origin.setStatus(Status.DOWN);
        return origin;
    }
}
