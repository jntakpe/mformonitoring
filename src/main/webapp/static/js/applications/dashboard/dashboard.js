mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.dashboard', {
        parent: 'applications.detail',
        url: '/applications/:id/dashboard',
        views: {
            'detail': {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController as dashboard'
            }
        },
        resolve: {
            application: function (ApplicationService, $stateParams) {
                return ApplicationService.application.get({id: $stateParams.id}).$promise;
            }
        }
    });

});