mfmApp.controller('MetricsController', MetricsController);

function MetricsController(MetricsService) {
    "use strict";

    var vm = this;
    vm.data = {};
    vm.refresh = function () {
        MetricsService.find.success(function (response) {
            vm.data = response;
            vm.stats = MetricsService.extractStats(response);
        });
    };

    vm.refresh();
}