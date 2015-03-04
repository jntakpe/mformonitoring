mfmApp.controller('ApplicationController', ApplicationController);
mfmApp.controller('EditApplicationModalController', EditApplicationModalController);

function ApplicationController(ApplicationService, $modal) {
    "use strict";

    var vm = this;
    vm.application = ApplicationService.application.query();
    vm.modal = function () {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal/edit-app.html',
            controller: 'EditApplicationModalController as editAppModal',
            size: 'lg'
        });

        modalInstance.result.then(function (application) {
            console.log(application);
        });
    };
}

function EditApplicationModalController($modalInstance) {
    "use strict";

    var vm = this;
    vm.application = {};
    vm.close = function () {
        $modalInstance.dismiss();
    };
    vm.save = function () {
        console.log(vm.application);
    };

}