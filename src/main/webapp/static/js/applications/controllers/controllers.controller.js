mfmApp.controller('ControllersController', function (application, MetricsService, PagingService) {
    "use strict";

    var vm = this;

    vm.refresh = function () {
        vm.stats = PagingService.process(vm.data, vm.search, vm.sort, vm.props);
    };
    vm.props = {};
    vm.data = {};
    vm.search = {};
    vm.sort = {
        class: []
    };
    MetricsService.findExternal(application.id, 'http://localhost:8080/manage/metrics').success(function (response) {
        vm.data = MetricsService.extractStats(response);
        vm.refresh();
    });
    vm.sortColumn = function (column) {
        PagingService.sort(column, vm.sort);
        vm.refresh();
    };
    vm.resetFilter = function () {
        vm.search = {};
        vm.refresh();
    };
});