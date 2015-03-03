mfmApp.controller('ApplicationController', ApplicationController);
mfmApp.controller('EditApplicationModalController', EditApplicationModalController);

function ApplicationController(ApplicationService, $modal) {
    "use strict";

    var vm = this;
    vm.application = ApplicationService.application.query();
    vm.modal = function () {
        $modal.open({
            templateUrl: 'views/modal/edit-application.html',
            controller: 'EditApplicationModalController as editAppModal',
            size: 'lg'
        });

    };
}

function EditApplicationModalController($modalInstance) {
    console.log($modalInstance);
}