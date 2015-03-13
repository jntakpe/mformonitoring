mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('partners', {
        parent: 'site',
        url: 'partners',
        views: {
            'content@': {
                templateUrl: 'views/partners.html',
                controller: 'PartnerController as partner'
            }
        }
    });

});