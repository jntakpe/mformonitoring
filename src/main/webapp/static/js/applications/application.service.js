mfmApp.factory('ApplicationService', function ($resource, $http) {
    "use strict";

    return {
        application: $resource('api/application/:id', {id: '@id'}),
        check: function (url) {
            return $http.get('api/application/check', {params: {url: url}});
        }
    };
});