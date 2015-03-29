mfmApp.factory('EnvironmentService', function () {
    "use strict";

    return {
        readableName: function (name) {
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
    };
});