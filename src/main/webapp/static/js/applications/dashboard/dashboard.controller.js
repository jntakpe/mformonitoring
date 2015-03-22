mfmApp.controller('DashboardController', function (application, MetricsService) {
    "use strict";

    var vm = this;
    vm.application = application;
    console.log(application);
    vm.data = {};
    vm.refresh = function () {
        MetricsService.findExternal(application.id, 'http://localhost:9080/manage/metrics').success(function (response) {
            vm.data = response;
        });
    };
    vm.refresh();
});