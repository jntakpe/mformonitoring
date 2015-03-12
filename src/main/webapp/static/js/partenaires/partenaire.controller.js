mfmApp.controller('PartenaireController', function (PartenaireService) {
    "use strict";

    var vm = this;
    vm.partenaires = PartenaireService.query();

});