mfmApp.controller('EnvironmentController', EnvironmentController);
mfmApp.controller('EnvironmentAppController', EnvironmentAppController);
mfmApp.controller('EnvironmentPartnerController', EnvironmentPartnerController);

function EnvironmentController(EnvironmentService, $stateParams) {
    "use strict";

    var vm = this;
    vm.name = $stateParams.name;
    vm.readableName = EnvironmentService.readableName(vm.name);
}

function EnvironmentAppController() {
    "use strict";

    var vm = this;
}

function EnvironmentPartnerController() {
    "use strict";

    var vm = this;
}