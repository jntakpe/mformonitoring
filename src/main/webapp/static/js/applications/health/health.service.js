mfmApp.factory('HealthService', function ($http) {
    "use strict";
    return {
        find: function (appId) {
            return $http.get('api/application/' + appId + '/health');
        }
    };
});