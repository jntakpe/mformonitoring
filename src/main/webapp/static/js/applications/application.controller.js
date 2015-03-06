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
            size: 'md'
        });
        modalInstance.result.then(function (application) {
            console.log(application);
        });
    };
}

function EditApplicationModalController(ApplicationService, $modalInstance) {
    "use strict";

    var vm = this;
    vm.urlChecked = false;
    vm.application = {
        environnement: 'DEVELOPPEMENT'
    };
    vm.close = function () {
        $modalInstance.dismiss();
    };
    vm.check = function (form) {
        if (form.$valid) {
            ApplicationService.check(vm.application.url).success(function (response) {
                vm.application.nom = response.nom;
                vm.application.groupId = response.groupId;
                vm.application.artifactId = response.artifactId;
                vm.application.version = response.version;
                vm.urlChecked = true;
            });
        }
    };
    vm.save = function () {
        console.log(vm.application);
    };

}