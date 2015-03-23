mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.dashboard', {
        url: '/dashboard',
        views: {
            'detail': {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController as dashboard'
            }
        }
    });

});