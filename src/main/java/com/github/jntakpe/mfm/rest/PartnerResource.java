package com.github.jntakpe.mfm.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.domain.Partner;
import com.github.jntakpe.mfm.mapper.PartnerMapper;
import com.github.jntakpe.mfm.service.ApplicationService;
import com.github.jntakpe.mfm.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Set;

/**
 * Ressource REST associée aux partenaires
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.PARTNER)
public class PartnerResource {

    private ApplicationService applicationService;

    private PartnerService partnerService;

    @Autowired
    public PartnerResource(ApplicationService applicationService, PartnerService partnerService) {
        this.applicationService = applicationService;
        this.partnerService = partnerService;
    }

    /**
     * Récupère les informations sur le statut de l'application et de ces partenaires puis les met à jour
     *
     * @param appId identifiant de l'application
     * @return application avec les partenaires mis à jour
     */
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Set<Partner>>> health(@RequestParam String appId) {
        DeferredResult<ResponseEntity<Set<Partner>>> deferred = new DeferredResult<>();
        Application app = applicationService.findByIdWithPartners(appId);
        partnerService.health(app.healthUrl()).addCallback(
                h -> deferred.setResult(new ResponseEntity<>(PartnerMapper.toSet(h.getBody(), app), HttpStatus.OK)),
                h -> deferred.setResult(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
        return deferred;
    }

    /**
     * Enregistre un partenaire
     *
     * @param partner partenaire à enregistrer
     * @return partenaire enregistré
     */
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Partner> save(@RequestBody Partner partner) {
        partner = partnerService.save(partner);
        partner.setApplications(applicationService.save(partner));
        return new ResponseEntity<>(partner, HttpStatus.OK);
    }
}
