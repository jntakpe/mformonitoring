mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.properties', {
        url: '/properties',
        abstract: true,
        views: {
            'detail': {
                templateUrl: 'views/properties.html',
                controller: 'PropertiesController as properties'
            }
        },
        resolve: {
            properties: function (PropertiesService) {
                return PropertiesService.extract();
            }
        }

    });

    $stateProvider.state('applications.detail.properties.params', {
        url: '/params',
        views: {
            'tab': {
                templateUrl: 'views/properties.params.html',
                controller: 'PropertiesParamsController as propParams'
            }
        }
    });

    $stateProvider.state('applications.detail.properties.env', {
        url: '/env',
        views: {
            'tab': {
                templateUrl: 'views/properties.env.html',
                controller: 'PropertiesEnvController as propEnv'
            }
        }
    });

    $stateProvider.state('applications.detail.properties.sys', {
        url: '/sys',
        views: {
            'tab': {
                templateUrl: 'views/properties.sys.html',
                controller: 'PropertiesSysController as propSys'
            }
        }
    });

});