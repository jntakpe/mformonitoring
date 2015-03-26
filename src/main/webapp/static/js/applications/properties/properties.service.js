mfmApp.factory('PropertiesService', function PropertiesService($http, environments) {
    "use strict";

    function find() {
        return $http.get('manage/env');
    }

    function extract() {
        return find().then(function (response) {
            var properties = {}, data = response.data, key, envIdx;
            properties.sys = {
                app: data.systemProperties,
                env: data.systemEnvironment
            };
            for (key in data) {
                if (data.hasOwnProperty(key)) {
                    if (key.indexOf('applicationConfig') !== -1 && key.indexOf('#') === -1) {
                        properties.app = data[key];
                    } else if (key.indexOf('profiles') !== -1) {
                        for (envIdx in environments) {
                            if (environments.hasOwnProperty(envIdx) && data[key].indexOf(environments[envIdx]) !== -1) {
                                properties.profile = environments[envIdx];
                                break;
                            }
                        }
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