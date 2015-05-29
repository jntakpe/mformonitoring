mfmApp.factory('EnvironmentService', function ($http) {
    "use strict";

    function readableName(name) {
        switch (name) {
            case 'DEVELOPPEMENT':
                return 'de développement';
            case 'ASSEMBLAGE':
                return 'd\'assemblage';
            case 'INTEGRATION':
                return 'd\'intégration';
            case 'RECETTE':
                return 'de recette';
            case 'PRE_PRODUCTION':
                return 'de pré-production';
            case 'PRODUCTION':
                return 'de production';
        }
    }

    function find(name) {
        return $http.get('api/application/env/' + name);
    }

    function addIfAbsent(partners, partner) {
        var idx;
        for (idx in partners) {
            if (partners.hasOwnProperty(idx)) {
                if (partners[idx].url === partner.url && partners[idx].name === partner.name) {
                    return;
                }
            }
        }
        partners.push(partner);
    }

    function extractPartners(apps) {
        var appIdx, allPartners = [], partIdx, partners;
        for (appIdx in apps) {
            if (apps.hasOwnProperty(appIdx)) {
                partners = apps[appIdx].partners;
                for (partIdx in partners) {
                    if (partners.hasOwnProperty(partIdx)) {
                        addIfAbsent(allPartners, partners[partIdx]);
                    }
                }
            }
        }
        return allPartners;
    }

    return {
        readableName: readableName,
        find: find,
        extractPartners: extractPartners
    };
});