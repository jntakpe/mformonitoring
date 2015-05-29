mfmApp.config(function ($stateProvider) {
    "use strict";

    $stateProvider.state('applications.detail.controllers', {
        url: '/controllers',
        views: {
            'detail': {
                templateUrl: 'views/controllers.html',
                controller: 'ControllersController as ctrls'
            }
        }
    });

});
