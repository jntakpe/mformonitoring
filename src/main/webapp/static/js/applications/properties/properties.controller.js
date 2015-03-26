mfmApp.controller('PropertiesController', PropertiesController);
mfmApp.controller('PropertiesParamsController', PropertiesParamsController);
mfmApp.controller('PropertiesEnvController', PropertiesEnvController);

function PropertiesController() {
    "use strict";

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
    vm.sortColumn = function (column) {
        PagingService.sort(column, vm.sort);
        vm.refresh();
    };
    vm.resetFilter = function () {
        vm.search = {};
        vm.refresh();
    };
}

function PropertiesEnvController() {
    "use strict";

    var vm = this;
}