mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('metrics', {
        parent: 'admin',
        url: '/metrics',
        views: {
            'content@': {
                templateUrl: 'views/metrics.html',
                controller: 'MetricsController as metrics'
            }
        }
    });
});