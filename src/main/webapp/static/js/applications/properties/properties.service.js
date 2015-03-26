mfmApp.factory('PropertiesService', function PropertiesService($http, environments) {
    "use strict";

    function find() {
        return $http.get('manage/env');
    }

    function extract() {
        return find().then(function (response) {
            var properties = {}, data = response.data, key, propKey, envIdx, prop;
            properties.sys = {
                app: data.systemProperties,
                env: data.systemEnvironment
            };
            for (key in data) {
                if (data.hasOwnProperty(key)) {
                    prop = data[key];
                    console.log(prop);
                    if (key.indexOf('applicationConfig') !== -1 && key.indexOf('#') === -1) {
                        properties.app = [];
                        for (propKey in prop) {
                            if (prop.hasOwnProperty(propKey)) {
                                properties.app.push({key: propKey, value: prop[propKey]});
                            }
                        }
                    } else if (key.indexOf('profiles') !== -1) {
                        for (envIdx in environments) {
                            if (environments.hasOwnProperty(envIdx) && prop.indexOf(environments[envIdx]) !== -1) {
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