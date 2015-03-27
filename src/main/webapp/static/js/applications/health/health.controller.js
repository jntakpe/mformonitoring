mfmApp.controller('HealthController', function (PartnerService, application) {
    "use strict";

    var vm = this;
    PartnerService.find(application.id).then(function (response) {
        vm.data = response.data;
    });
});