mfmApp.factory('PropertiesService', function PropertiesService($http) {
    "use strict";

    function find() {
        return $http.get('manage/env');
    }

    function extract() {
        return find().then(function (response) {
            var properties = {}, data = response.data, key;
            properties.profiles = data.profiles;
            properties.sys = {
                app: data.systemProperties,
                env: data.systemEnvironment
            };
            for (key in data) {
                if (data.hasOwnProperty(key)) {
                    if (key.indexOf('applicationConfig') !== -1 && key.indexOf('#') === -1) {
                        properties.app = data[key];
                    }
                }
            }
            return properties;
        });
    }

    return {
        extract: extract
    };
});