mfmApp.controller('HomeController', function (HomeService) {
    "use strict";

    var vm = this;
    HomeService.findAll().success(function (data) {
        vm.notifications = HomeService.display(data);
    });
});
