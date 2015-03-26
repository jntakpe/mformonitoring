mfmApp.controller('PropertiesController', PropertiesController);
mfmApp.controller('PropertiesParamsController', PropertiesParamsController);
mfmApp.controller('PropertiesEnvController', PropertiesEnvController);

function PropertiesController(properties) {
    "use strict";

    var vm = this;
    vm.profile = properties.profile;
}

function PropertiesParamsController(properties, PagingService) {
    "use strict";

    var vm = this;
    vm.refresh = function () {
        vm.data = PagingService.process(properties.app, vm.search, vm.sort, vm.props);
    };
    vm.data = {};
    vm.search = {};
    vm.sort = {
        class: []
    };
    vm.props = PagingService.toListParams(properties, 4);
    vm.refresh();
}

function PropertiesEnvController() {
    "use strict";

    var vm = this;
}