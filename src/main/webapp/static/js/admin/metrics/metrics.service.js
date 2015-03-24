mfmApp.factory('MetricsService', MetricsService);

function MetricsService($http) {
    "use strict";

    function resourceStats(stats) {
        var result = [], classPath, length;
        angular.forEach(stats.timers, function (value, key) {
            if (key.indexOf('Controller') !== -1 || key.indexOf('Resource') !== -1) {
                value.path = key;
                classPath = key.split('.');
                length = classPath.length;
                value.method = classPath[length - 1];
                value.class = classPath[length - 2];
                result.push(value);
            }
        });
        return result;
    }

    return {
        find: $http.get('manage/metrics'),
        findExternal: function (id, url) {
            return $http.get('api/application/' + id + '/metrics', {params: {url: url}});
        },
        checkHealth: function () {
            return $http.get('manage/health').then(function (response) {
                return response.data;
            });
        },
        threadDump: function () {
            return $http.get('manage/dump').then(function (response) {
                return response.data;
            });
        },
        extractStats: function (stats) {
            return resourceStats(stats);
        }
    };
}