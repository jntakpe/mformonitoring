mfmApp.factory('PartenaireService', function ($resource) {
    "use strict";

    return $resource('api/partenaire');
});