mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications', {
        parent: 'site',
        url: '/applications',
        views: {
            'content@': {
                templateUrl: 'views/applications.html',
                controller: 'ApplicationController as application'
            }
        }
    });

    $stateProvider.state('applications.detail', {
        parent: 'site',
        url: '/applications/:id',
        views: {
            'content@': {
                templateUrl: 'views/applications.detail.html',
                controller: 'ApplicationDetailController as appDetail'
            }
        },
        resolve: {
            application: function (ApplicationService, $stateParams) {
                return ApplicationService.application.get({id: $stateParams.id}).$promise;
            }
        }
    });

});