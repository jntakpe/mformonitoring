mfmApp.controller('ControllersController', function (application, MetricsService, PagingService) {
    "use strict";
    var vm = this;
    vm.refresh = function () {
        vm.stats = PagingService.process(vm.data, vm.search, vm.sort, vm.props);
    };
    vm.data = {};
    vm.search = {};
    vm.sort = {
        class: {count: 'fa-sort-desc'},
        column: 'count',
        reverse: true
    };
    MetricsService.findExternal(application.id, MetricsService.toMetricsUrl(application.url))
        .success(function (response) {
            vm.data = MetricsService.extractStats(response);
            vm.props = PagingService.toListParams(vm.data, 12);
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