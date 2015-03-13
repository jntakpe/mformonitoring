package com.github.jntakpe.mfm.rest;

import com.github.jntakpe.mfm.domain.Application;
import com.github.jntakpe.mfm.mapper.ApplicationMapper;
import com.github.jntakpe.mfm.service.ApplicationService;
import com.github.jntakpe.mfm.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Optional;

/**
 * Ressouce REST associé à une application
 *
 * @author jntakpe
 */
@RestController
@RequestMapping(Urls.APPLICATION)
public class ApplicationResource {

    private ApplicationService applicationService;

    private PartnerService partnerService;

    @Autowired
    public ApplicationResource(ApplicationService applicationService, PartnerService partnerService) {
        this.applicationService = applicationService;
        this.partnerService = partnerService;
    }

    /**
     * Renvoie la liste des applications
     *
     * @return la liste des applications
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Application> list() {
        return applicationService.findAll();
    }

    /**
     * Vérification de l'URL de monitoring. Si l'url est déjà utilisée renvoie un code d'erreur HTTP 409.
     * Si la récupération des informations échoue à l'url donnée renvoie un code d'erreur 500.
     *
     * @param url url de monitoring à tester
     * @param id  identifiant de l'application dans le cas d'une application déjà existante
     * @return la récupération des informations du projet sinon un code erreur
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Application> check(@RequestParam String url, @RequestParam(required = false) Long id) {
        Optional<Application> app = applicationService.findByUrl(url);
        if (app.isPresent() && !app.get().getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(applicationService.findAppInfos(url), HttpStatus.OK);
    }

    /**
     * Ajout d'une nouvelle application
     *
     * @param application application à ajouter
     * @return application enregistrée
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Application> create(@RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Modification d'une application
     *
     * @param id          identifiant de l'application
     * @param application application à modifier
     * @return application modifiée
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application application) {
        return new ResponseEntity<>(applicationService.save(application), HttpStatus.OK);
    }

    /**
     * Suppression de l'application possédant l'id
     *
     * @param id identifiant de l'application à supprimer
     * @return code HTTP 200 si la suppression est effectuée
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long id) {
        applicationService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Récupère les informations sur le statut de l'application et de ces partenaires puis les met à jour
     *
     * @param id identifiant de l'application
     * @return application avec les partenaires mis à jour
     */
    @RequestMapping(value = "/{id}/health", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<Application>> health(@PathVariable Long id) {
        DeferredResult<ResponseEntity<Application>> deferred = new DeferredResult<>();
        Application app = applicationService.findByIdWithPartners(id);
        partnerService.health(app.getUrl()).addCallback(
                h -> {
                    Application saved = ApplicationMapper.map(h.getBody(), app);
                    deferred.setResult(new ResponseEntity<>(saved, HttpStatus.OK));
                },
                h -> deferred.setResult(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)));
        return deferred;
    }
}
