mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('environment', {
        abstract: true,
        parent: 'site',
        url: '/environment/:name',
        views: {
            'content@': {
                templateUrl: 'views/environment.html',
                controller: 'EnvironmentController as env'
            }
        }
    });

    $stateProvider.state('environment.app', {
        url: '/applications',
        views: {
            'detail': {
                templateUrl: 'views/environment.app.html',
                controller: 'EnvironmentAppController as envApp'
            }
        }
    });

    $stateProvider.state('environment.partner', {
        url: '/partners',
        views: {
            'detail': {
                templateUrl: 'views/environment.partner.html',
                controller: 'EnvironmentPartnerController as envPartner'
            }
        }
    });
});