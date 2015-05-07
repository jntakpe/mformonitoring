mfmApp.controller('LogsController', function (application, LogsService, PagingService) {
    'use strict';

    var vm = this;

    vm.refresh = function () {
        var search = angular.copy(vm.search);
        if (vm.onlyBfb) {
            search.name = 'com.bforbank';
            if (vm.search.name) {
                search.name += '.' + vm.search.name;
            }
        }
        vm.loggers = PagingService.process(vm.data, search, vm.sort, vm.props);
    };

    vm.onlyBfb = true;
    vm.data = {};
    vm.search = {};
    vm.sort = {
        class: {count: 'fa-sort-desc'},
        column: 'name',
        reverse: true
    };

    function reInit() {
        LogsService.findAll(application.id).success(function (response) {
            vm.data = response;
            vm.props = PagingService.toListParams(vm.data, 12);
            vm.refresh();
        });
    }

    vm.changeLevel = function (logger, level) {
        LogsService.update(application.id, {name: logger.name, level: level}).success(function () {
            reInit();
        });
    };

    vm.sortColumn = function (column) {
        PagingService.sort(column, vm.sort);
        vm.refresh();
    };


    vm.resetFilter = function () {
        vm.search = {};
        vm.refresh();
    };

    reInit();
});