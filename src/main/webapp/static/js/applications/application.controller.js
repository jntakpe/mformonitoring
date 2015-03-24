mfmApp.controller('ApplicationController', ApplicationController);
mfmApp.controller('EditApplicationModalController', EditApplicationModalController);
mfmApp.controller('RemoveApplicationModalController', RemoveApplicationModalController);
mfmApp.controller('ApplicationDetailController', ApplicationDetailController);


function ApplicationController(ApplicationService, PagingService, AlertService, $modal) {
    "use strict";

    var vm = this, applications;

    vm.refresh = function () {
        vm.applications = PagingService.process(applications, vm.search, vm.sort, vm.props);
    };
    vm.alert = {
        active: false
    };
    vm.props = {};
    vm.search = {};
    vm.sort = {
        class: []
    };
    applications = ApplicationService.application.query();
    vm.editModal = function (application) {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal/edit-application.html',
            controller: 'EditApplicationModalController as editAppModal',
            size: 'md',
            resolve: {
                application: function () {
                    return application;
                }
            }
        });
        modalInstance.result.then(function (result) {
            applications.push(result.data);
            vm.refresh();
            vm.alert = AlertService.success(result.action + ' de l\'application effectuée avec succès.');
        });
    };
    vm.removeModal = function (application) {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal/remove-application.html',
            controller: 'RemoveApplicationModalController as removeAppModal',
            size: 'md',
            resolve: {
                application: function () {
                    return application;
                }
            }
        });
        modalInstance.result.then(function (application) {
            ApplicationService.remove(application, applications);
            vm.refresh();
            vm.alert = AlertService.success('Suppression de l\'application effectuée avec succès.');
        });
    };
    applications.$promise.then(function () {
        vm.props = PagingService.toListParams(applications, 8);
        vm.refresh();
    });
    vm.sortColumn = function (column) {
        PagingService.sort(column, vm.sort);
        vm.refresh();
    };
    vm.resetFilter = function () {
        vm.search = {};
        vm.refresh();
    };
}

function EditApplicationModalController(ApplicationService, $modalInstance, application) {
    "use strict";

    var vm = this;
    vm.urlChecked = false;
    if (application) {
        vm.application = application;
    } else {
        vm.application = {
            environment: 'DEVELOPPEMENT'
        };
    }
    vm.close = function () {
        $modalInstance.dismiss();
    };
    vm.cancel = function () {
        vm.urlChecked = false;
    };
    vm.check = function (form) {
        ApplicationService.check(vm.application).success(function (response) {
            vm.application.name = response.name;
            vm.application.groupId = response.groupId;
            vm.application.artifactId = response.artifactId;
            vm.application.version = response.version;
            vm.urlChecked = true;
        }).error(function (data, status) {
            form.url.$setValidity(status === 409 ? 'conflict' : 'check', false);
        });
    };
    vm.save = function () {
        if (vm.application.id) {
            ApplicationService.application.update(vm.application, function () {
                $modalInstance.close({data: application, action: 'Modication'});
            });
        } else {
            ApplicationService.application.save(vm.application, function (application) {
                $modalInstance.close({data: application, action: 'Création'});
            });
        }
    };
}

function RemoveApplicationModalController(ApplicationService, $modalInstance, application) {
    "use strict";

    var vm = this;
    vm.application = application;
    vm.cancel = function () {
        $modalInstance.dismiss();
    };
    vm.delete = function () {
        ApplicationService.application.delete({}, {id: vm.application.id}, function (application) {
            $modalInstance.close(application);
        });
    };
}

function ApplicationDetailController(ApplicationService, $state, application) {
    "use strict";

    var vm = this;
    vm.application = application;
    vm.label = ApplicationService.stateLabel($state);
    vm.readableName = ApplicationService.readableName(application);
    vm.refresh = function () {
        $state.go($state.current, {}, {reload: true});
    };
}