mfmApp.controller('ApplicationController', ApplicationController);
mfmApp.controller('EditApplicationModalController', EditApplicationModalController);
mfmApp.controller('RemoveApplicationModalController', RemoveApplicationModalController);

function ApplicationController(ApplicationService, PagingService, $modal, $filter) {
    "use strict";

    var vm = this, applications;

    function refresh() {
        var filtered, sorted, paginated;
        filtered = $filter('filter')(applications, vm.search);
        sorted = $filter('orderBy')(filtered, vm.sort.column, vm.sort.reverse);
        paginated = PagingService.paginate(vm.props.current, vm.props.numberPerPage, sorted);
        vm.applications = paginated.data;
        vm.props.total = paginated.total;
    }

    vm.search = {};
    vm.alert = {
        active: false
    };
    vm.sort = {
        class: []
    };
    applications = ApplicationService.application.query();
    vm.editModal = function () {
        var modalInstance = $modal.open({
            templateUrl: 'views/modal/edit-application.html',
            controller: 'EditApplicationModalController as editAppModal',
            size: 'md'
        });
        modalInstance.result.then(function (application) {
            applications.push(application);
            refresh();
            vm.alert.type = 'success';
            vm.alert.msg = 'Création de l\'application effectuée avec succès.';
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
            refresh();
            vm.alert.type = 'success';
            vm.alert.msg = 'Suppression de l\'application effectuée avec succès.';
        });
    };
    applications.$promise.then(function () {
        vm.props = PagingService.toListParams(applications, 8);
        refresh();
    });
    vm.sortColumn = function (column) {
        PagingService.sort(column, vm.sort);
        refresh();
    };
    vm.pageChange = function () {
        refresh();
    };
    vm.filter = function () {
        refresh();
    };
    vm.resetFilter = function () {
        vm.search = {};
        refresh();
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
    vm.cancel = function () {
        vm.urlChecked = false;
    };
    vm.check = function (form) {
        ApplicationService.check(vm.application.url).success(function (response) {
            vm.application.nom = response.nom;
            vm.application.groupId = response.groupId;
            vm.application.artifactId = response.artifactId;
            vm.application.version = response.version;
            vm.urlChecked = true;
        }).error(function (data, status) {
            form.url.$setValidity(status === 409 ? 'conflict' : 'check', false);
        });
    };
    vm.save = function () {
        ApplicationService.application.save(vm.application, function (application) {
            $modalInstance.close(application);
        });
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