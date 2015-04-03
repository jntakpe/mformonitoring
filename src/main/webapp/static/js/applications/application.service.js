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

    function readableName(application) {
        return application.artifactId === 'bss' ? application.name : 'l\'' + application.name;
    }

    function icon(status) {
        var label;
        if (status === 'UP') {
            label = {
                icon: 'fa-thumbs-up',
                class: 'u'
            };
        } else if (status === 'DOWN') {
            label = {
                icon: 'fa-thumbs-down',
                class: 'danger'
            };
        } else {
            label = {
                icon: 'fa-cog',
                class: 'warning'
            };
        }
        return label;
    }

    return {
        application: $resource('api/application/:id', {id: '@id'}, {update: {method: 'PUT'}}),
        check: check,
        remove: remove,
        readableName: readableName,
        icon: icon
    };
});