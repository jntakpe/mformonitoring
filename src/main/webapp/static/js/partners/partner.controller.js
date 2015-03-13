mfmApp.controller('PartnerController', function (PartnerService) {
    "use strict";

    var vm = this;

    vm.partners = PartnerService.refresh();
});