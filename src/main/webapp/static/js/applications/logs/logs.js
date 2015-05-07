mfmApp.config(function ($stateProvider) {
    'use strict';
    $stateProvider.state('applications.detail.logs', {
        url: '/logs',
        views: {
            'detail': {
                templateUrl: 'views/logs.html',
                controller: 'LogsController as logs'
            }
        }
    });
});