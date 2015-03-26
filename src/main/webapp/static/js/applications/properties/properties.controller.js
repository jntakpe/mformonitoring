mfmApp.controller('PropertiesController', PropertiesController);
mfmApp.controller('PropertiesParamsController', PropertiesParamsController);
mfmApp.controller('PropertiesEnvController', PropertiesEnvController);

function PropertiesController() {
    "use strict";

}

function PropertiesParamsController(properties, PropertiesService) {
    "use strict";

    var vm = this;
    PropertiesService.initKeyValueList(vm, properties.app);
}

function PropertiesSysController(properties, PagingService) {
    "use strict";

    var vm = this;

}

function PropertiesEnvController() {
    "use strict";

    var vm = this;
}

