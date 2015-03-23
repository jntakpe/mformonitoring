mfmApp.factory('ApplicationService', function ($resource, $http) {
    "use strict";

    function check(app) {
        var params = {url: app.url};
        if (app.id) {
            params.id = app.id;
        }
        return $http.get('api/application/check', {params: params});
    }

    function remove(elem, list) {
        var idx;
        for (idx in list) {
            if (list.hasOwnProperty(idx) && list[idx].id === elem.id) {
                list.splice(idx, 1);
            }
        }
    }

    function stateLabel(state) {
        var name = state.current.name;
        switch (name) {
            case 'applications.detail.controllers':
                return 'Statistiques';
            case 'applications.detail.properties':
                return 'Paramétrage';
            default:
                return 'Tableau de bord';
        }
    }

    function readableName(application) {
        return application.artifactId === 'bss' ? application.name : 'l\'' + application.name;
    }

    return {
        application: $resource('api/application/:id', {id: '@id'}, {update: {method: 'PUT'}}),
        check: check,
        remove: remove,
        stateLabel: stateLabel,
        readableName: readableName
    };
});