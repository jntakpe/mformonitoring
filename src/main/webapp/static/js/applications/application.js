mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications', {
        parent: 'site',
        url: 'applications',
        views: {
            'content@': {
                templateUrl: 'views/applications.html',
                controller: 'ApplicationController as application'
            }
        }
    });

});