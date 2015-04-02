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
     * Mise à jour d'une app existante à partir des informations récupérées
     *
     * @param origin app existante stockée en DB
     * @param infos  informations récupérées par endpoint REST
     * @return l'app existante avec les informations mises à jour
     */
    public static Application up(Application origin, Application infos) {
        origin.setStatus(Status.UP);
        origin.setArtifactId(infos.getArtifactId());
        origin.setGroupId(infos.getGroupId());
        origin.setVersion(infos.getVersion());
        return origin;
    }

    /**
     * Mise à jour d'une app existante au statut DOWN
     *
     * @param origin app existante stockée en DB
     * @return l'app existante avec le statut DOWN
     */
    public static Application down(Application origin) {
        origin.setStatus(Status.DOWN);
        return origin;
    }

}
