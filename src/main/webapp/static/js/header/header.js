mfmApp.config(function ($stateProvider) {
    $stateProvider.state('site', {
        abstract: true,
        views: {
            'header@': {
                templateUrl: 'views/header.html',
                controller: 'HeaderController as header'
            }
        }
    });
});