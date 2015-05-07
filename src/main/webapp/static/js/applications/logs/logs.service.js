mfmApp.factory('LogsService', function ($http) {
    'use strict';

    function findAll(id) {
        return $http.get('api/application/' + id + ' /logs');
    }

    function update(id, logger) {
        return $http.put('api/application/' + id + ' /logs', logger);
    }

    return {
        findAll: findAll,
        update: update
    };
});