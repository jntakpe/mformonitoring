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

    return {
        readableName: readableName,
        find: find
    };
});