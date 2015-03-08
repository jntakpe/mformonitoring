mfmApp.factory('ApplicationService', function ($resource, $http) {
    "use strict";

    return {
        application: $resource('api/application/:id', {id: '@id'}),
        check: function (url) {
            return $http.get('api/application/check', {params: {url: url}});
        },
        remove: function (elem, list) {
            var idx;
            for (idx in list) {
                if (list.hasOwnProperty(idx) && list[idx].id === elem.id) {
                    list.splice(idx, 1);
                }
            }
        }
    };
});