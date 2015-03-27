mfmApp.factory('PartnerService', function (ApplicationService, $http, $q, $resource) {
    "use strict";

    function find(id) {
        return $http.get('api/partner', {params: {appId: id}});
    }

    function addIfAbsent(partners, partner) {
        var idx, applications;
        for (idx in partners) {
            if (partners.hasOwnProperty(idx)) {
                if (partners[idx].url === partner.url) {
                    applications = partners[idx].applications || [];
                    applications.push(partner.applications[0]);
                    return;
                }
            }
        }
        partners.push(partner);
    }

    function whenAllDone(promises, deferDone) {
        $q.all(promises).then(function () {
            deferDone.resolve();
        }, function () {
            deferDone.reject();
        });
    }

    function refreshAll(deferDone) {
        var partners = [];
        ApplicationService.application.query(function (applications) {
            var promises = [];
            angular.forEach(applications, function (app) {
                var deferred = find(app.id);
                deferred.then(function (response) {
                    var idx, data = response.data;
                    for (idx in data) {
                        if (data.hasOwnProperty(idx)) {
                            addIfAbsent(partners, data[idx]);
                        }
                    }
                });
                promises.push(deferred);
            });
            whenAllDone(promises, deferDone);
        });
        return partners;
    }

    return {
        refresh: refreshAll,
        find: find,
        resource: $resource('api/partner/:id', {id: '@id'})
    };
});