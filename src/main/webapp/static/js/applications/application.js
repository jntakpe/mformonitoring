mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications', {
        parent: 'site',
        url: 'applications',
        views: {
            'content@': {
                templateUrl: 'views/application.html',
                controller: 'ApplicationController as application'
            }
        }
    });

});