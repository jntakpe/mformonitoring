mfmApp.controller('DashboardController', function (application, MetricsService) {
    "use strict";

    var vm = this;
    vm.data = {};
    vm.refresh = function () {
        MetricsService.findExternal(application.id, 'https://fra.herokuapp.com/rest/eers/manage/metrics').success(function (response) {
            vm.data = response;
        });
    };
    vm.refresh();
});