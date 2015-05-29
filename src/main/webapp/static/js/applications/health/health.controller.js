mfmApp.controller('HealthController', function (ApplicationService, PartnerService, application) {
    "use strict";

    var vm = this;
    PartnerService.find(application.id).then(function (response) {
        vm.data = response.data;
    });
    vm.icon = ApplicationService.icon;
});