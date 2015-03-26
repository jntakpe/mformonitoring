mfmApp.factory('PropertiesService', function PropertiesService(PagingService, $http) {
    "use strict";

    function find() {
        return $http.get('manage/env');
    }

    function literalToArray(literal) {
        var array = [], entry;
        for (entry in literal) {
            if (literal.hasOwnProperty(entry)) {
                array.push({key: entry, value: literal[entry]});
            }
        }
        return array;
    }

    function extract() {
        return find().then(function (response) {
            var properties = {}, data = response.data, key;
            properties.sys = {
                app: literalToArray(data.systemProperties),
                env: literalToArray(data.systemEnvironment)
            };
            for (key in data) {
                if (data.hasOwnProperty(key)) {
                    if (key.indexOf('applicationConfig') !== -1 && key.indexOf('#') === -1) {
                        properties.app = literalToArray(data[key]);
                    }
                }
            }
            return properties;
        });
    }

    function initKeyValueList(vm, data) {
        vm.refresh = function () {
            vm.data = PagingService.process(data, vm.search, vm.sort, vm.props);
        };
        vm.data = {};
        vm.search = {};
        vm.sort = {
            class: []
        };
        vm.props = PagingService.toListParams(data, 10);
        vm.refresh();
        vm.sortColumn = function (column) {
            PagingService.sort(column, vm.sort);
            vm.refresh();
        };
        vm.resetFilter = function () {
            vm.search = {};
            vm.refresh();
        };

    }

    return {
        extract: extract,
        initKeyValueList: initKeyValueList
    };
});