mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('partenaires', {
        parent: 'site',
        url: 'partenaires',
        views: {
            'content@': {
                templateUrl: 'views/partenaires.html',
                controller: 'PartenaireController as partenaire'
            }
        }
    });

});