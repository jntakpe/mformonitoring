mfmApp.controller('PropertiesController', PropertiesController);
mfmApp.controller('PropertiesParamsController', PropertiesParamsController);
mfmApp.controller('PropertiesEnvController', PropertiesEnvController);
mfmApp.controller('PropertiesSysController', PropertiesSysController);

function PropertiesController() {
    "use strict";

}

function PropertiesParamsController(properties, PropertiesService) {
    "use strict";

    var vm = this;
    PropertiesService.initKeyValueList(vm, properties.app);
}

function PropertiesSysController(properties, PropertiesService) {
    "use strict";

    var vm = this;
    PropertiesService.initKeyValueList(vm, properties.sys.app);
}

function PropertiesEnvController(properties, PropertiesService) {
    "use strict";

    var vm = this;
    PropertiesService.initKeyValueList(vm, properties.sys.env);
}

