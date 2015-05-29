mfmApp.config(function ($stateProvider) {
    $stateProvider
        .state('admin', {
            abstract: true,
            data: {
                roles: []
            },
            parent: 'site'
        });
});
