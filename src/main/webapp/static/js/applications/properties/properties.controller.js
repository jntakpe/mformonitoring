mfmApp.controller('PropertiesController', PropertiesController);
mfmApp.controller('PropertiesParamsController', PropertiesParamsController);
mfmApp.controller('PropertiesEnvController', PropertiesEnvController);

function PropertiesController(properties) {
    "use strict";

    var vm = this;
    vm.profiles = properties.properties;
}

function PropertiesParamsController(properties) {
    "use strict";

    var vm = this;
    vm.data = properties.app;
}

function PropertiesEnvController() {
    "use strict";

    var vm = this;
}