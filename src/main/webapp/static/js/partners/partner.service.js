mfmApp.factory('PartnerService', function (ApplicationService, $http) {
    "use strict";

    return {
        refresh: function () {
            var partners = [];
            ApplicationService.application.query(function (applications) {
                angular.forEach(applications, function (app) {
                    $http.get('api/application/' + app.id + '/health').success(function (application) {
                        console.log(application);
                    });

                });
            });
        }
    };
});