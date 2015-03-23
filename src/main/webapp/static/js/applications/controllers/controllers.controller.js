mfmApp.controller('ControllersController', function (application, MetricsService) {
    "use strict";

    var vm = this;
    vm.stats = {};
    MetricsService.findExternal(application.id, 'http://localhost:8080/manage/metrics').success(function (response) {
        vm.stats = MetricsService.extractStats(response);
    });
});