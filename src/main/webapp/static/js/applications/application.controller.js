mfmApp.controller('ApplicationController', function (ApplicationService) {
    "use strict";

    var vm = this;
    vm.application = ApplicationService.application.query();
});
