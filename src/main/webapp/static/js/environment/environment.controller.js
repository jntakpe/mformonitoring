mfmApp.controller('EnvironmentController', EnvironmentController);

function EnvironmentController(EnvironmentService, $stateParams) {
    "use strict";

    var vm = this;
    vm.name = $stateParams.name;
    vm.readableName = EnvironmentService.readableName(vm.name);
    EnvironmentService.find(vm.name).then(function (response) {
        vm.apps = response.data;
    });
}