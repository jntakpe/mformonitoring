mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('home', {
            parent: 'site',
            url: '/',
            views: {
                'content@': {
                    templateUrl: 'views/home.html',
                    controller: 'HomeController as home'
                }
            }
        });
});