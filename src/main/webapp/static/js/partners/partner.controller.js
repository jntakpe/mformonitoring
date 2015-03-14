mfmApp.controller('PartnerController', function (PartnerService, $q) {
    "use strict";

    var vm = this, allDone = $q.defer();

    allDone.promise.then(function () {
        console.log("Saving");
    });

    vm.partners = PartnerService.refresh(allDone);
});