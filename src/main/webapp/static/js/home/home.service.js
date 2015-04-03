mfmApp.factory('HomeService', function ($http) {
    "use strict";

    function display(notifs) {
        var idx, notif;
        for (idx in notifs) {
            if (notifs.hasOwnProperty(idx)) {
                notif = notifs[idx];
                notif.display = extractInfo(notif);
            }
        }
        return notifs;
    }

    function extractInfo(notif) {
        var display = {}, name = notif.appInfos.name, environment = notif.appInfos.environment;
        if (notif.type === 'START') {
            display.icon = 'fa-thumbs-up icon-bg-green';
            display.alert = 'alert-blocks-success';
            display.color = 'color-green';
            display.title = 'Application démarrée';
        } else if (notif.type === 'STOP') {
            display.icon = 'fa-thumbs-down icon-bg-red';
            display.alert = 'alert-blocks-error';
            display.color = 'color-red';
            display.title = 'Application arrêtée';
        } else {
            display.icon = 'fa-history icon-bg-blue';
            display.alert = 'alert-blocks-info';
            display.color = 'color-blue';
            display.title = 'Nouvelle version';
        }
        return display;
    }

    return {
        findAll: $http.get('api/notification'),
        display: display
    }
});