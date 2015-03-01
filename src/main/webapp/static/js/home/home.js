mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider
        .state('home', {
            parent: 'site',
            url: '/',
            data: {
                roles: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/home.html',
                    controller: 'HomeController as home'
                }
            }
        });
});