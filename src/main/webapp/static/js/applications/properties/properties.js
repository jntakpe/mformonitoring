mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.properties', {
        url: '/properties',
        views: {
            'detail': {
                templateUrl: 'views/properties.html',
                controller: 'PropertiesController as properties'
            }
        }
    });
});