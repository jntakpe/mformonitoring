mfmApp.factory('PartnerService', function (ApplicationService, $http, $q) {
    "use strict";

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

    function allDone(promises, deferDone) {
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
                var deferred = $http.get('api/application/' + app.id + '/health');
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
            allDone(promises, deferDone);
        });
        return partners;
    }

    return {
        refresh: refreshAll
    };
});