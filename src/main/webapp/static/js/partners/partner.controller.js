mfmApp.controller('PartnerController', function (PartnerService, ApplicationService, $q) {
    "use strict";

    var vm = this, allDone = $q.defer();

    allDone.promise.then(function () {
        angular.forEach(vm.partners, function (partner) {
            PartnerService.resource.save(partner);
        });
    });

    vm.partners = PartnerService.refresh(allDone);

    vm.appColor = ApplicationService.appColor;
});