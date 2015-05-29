mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.health', {
        url: '/health',
        views: {
            'detail': {
                templateUrl: 'views/health.html',
                controller: 'HealthController as health'
            }
        }
    });
});