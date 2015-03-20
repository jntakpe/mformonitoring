mfmApp.controller('MetricsController', MetricsController);

function MetricsController(Metrics) {
    "use strict";

    var vm = this;
    vm.data = {};
    vm.refresh = function () {
        Metrics.findMetrics().then(function (response) {
            vm.data = response;
            console.log(response);
            vm.stats = Metrics.extractStats(response);
            console.log(vm.stats);
        });
    };

    vm.refresh();
}