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

mfmApp.directive('perfectScrollbar',
    ['$parse', '$window', function($parse, $window) {
        var psOptions = [
            'wheelSpeed', 'wheelPropagation', 'minScrollbarLength', 'useBothWheelAxes',
            'useKeyboard', 'suppressScrollX', 'suppressScrollY', 'scrollXMarginOffset',
            'scrollYMarginOffset', 'includePadding'
        ];
        return {
            restrict: 'A',
            transclude: true,
            template: '<div ng-transclude></div>',
            replace: true,
            link: function($scope, $elem, $attr) {
                var jqWindow = angular.element($window);
                var options = {};
                for (var i=0, l=psOptions.length; i<l; i++) {
                    var opt = psOptions[i];
                    if ($attr[opt] !== undefined) {
                        options[opt] = $parse($attr[opt])();
                    }
                }
                $scope.$evalAsync(function() {
                    $elem.perfectScrollbar(options);
                    var onScrollHandler = $parse($attr.onScroll);
                    $elem.scroll(function(){
                        var scrollTop = $elem.scrollTop();
                        var scrollHeight = $elem.prop('scrollHeight') - $elem.height();
                        $scope.$apply(function() {
                            onScrollHandler($scope, {
                                scrollTop: scrollTop,
                                scrollHeight: scrollHeight
                            })
                        })
                    });
                });
                function update(event) {
                    $scope.$evalAsync(function() {
                        if ($attr.scrollDown == 'true' && event != 'mouseenter') {
                            setTimeout(function () {
                                $($elem).scrollTop($($elem).prop("scrollHeight"));
                            }, 100);
                        }
                        $elem.perfectScrollbar('update');
                    });
                }
                $elem.bind('mouseenter', update('mouseenter'));
                if ($attr.refreshOnChange) {
                    $scope.$watchCollection($attr.refreshOnChange, function() {
                        update();
                    });
                }
                if ($attr.refreshOnResize) {
                    jqWindow.on('resize', update);
                }
                $elem.bind('$destroy', function() {
                    jqWindow.off('resize', update);
                    $elem.perfectScrollbar('destroy');
                });
            }
        };
    }]);