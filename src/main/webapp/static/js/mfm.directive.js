mfmApp.directive('wrap', function () {
    return {
        restrict: 'A',
        link: function (scope, elem) {
            elem.addClass('wrap');
            elem.bind('click', function () {
                elem.toggleClass('wrap');
            });
        }
    };
});