mfmApp.controller('DashboardController', function (application, MetricsService) {
    "use strict";

    var vm = this;
    vm.data = {};
    vm.refresh = function () {
        MetricsService.findExternal(application.id, MetricsService.toMetricsUrl(application.url))
            .success(function (response) {
                vm.data = response;
            });
    };
    vm.refresh();
});