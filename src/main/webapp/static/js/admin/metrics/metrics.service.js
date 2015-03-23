mfmApp.factory('MetricsService', MetricsService);

function MetricsService($http) {
    "use strict";

    function resourceStats(stats) {
        var result = {};
        angular.forEach(stats.timers, function (value, key) {
            if (key.indexOf('com.github.jntakpe.qpq.web') !== -1) {
                result[key] = value;
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