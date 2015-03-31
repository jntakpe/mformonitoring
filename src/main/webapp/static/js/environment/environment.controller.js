mfmApp.controller('EnvironmentController', EnvironmentController);

function EnvironmentController(ApplicationService, EnvironmentService, $stateParams) {
    "use strict";

    var vm = this;
    vm.name = $stateParams.name;
    vm.readableName = EnvironmentService.readableName(vm.name);
    EnvironmentService.find(vm.name).then(function (response) {
        vm.apps = response.data;
        vm.partners = EnvironmentService.extractPartners(vm.apps);
    });
    vm.icon = ApplicationService.icon;
}