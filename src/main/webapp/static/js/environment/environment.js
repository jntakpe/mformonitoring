mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('environment', {
        parent: 'site',
        url: '/environment/:name',
        views: {
            'content@': {
                templateUrl: 'views/environment.html',
                controller: 'EnvironmentController as env'
            }
        }
    });

});