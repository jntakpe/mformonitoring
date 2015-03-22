mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.dashboard', {
        url: '/applications/:id/dashboard',
        views: {
            'detail': {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController as dashboard'
            }
        }
    });

});