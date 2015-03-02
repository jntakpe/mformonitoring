mfmApp.factory('ApplicationService', function ($resource) {
    "use strict";
    return {
        application: $resource('api/application')
    };
});