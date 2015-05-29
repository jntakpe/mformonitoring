mfmApp.factory('HomeService', function ($http) {
    "use strict";

    function extractInfo(notif) {
        var diplayInfos = {};
        diplayInfos.time = moment(new Date(notif.createdAt)).locale('fr').fromNow();
        if (notif.type === 'START') {
            diplayInfos.icon = 'fa-thumbs-up icon-bg-green';
            diplayInfos.alert = 'alert-blocks-success';
            diplayInfos.color = 'color-green';
            diplayInfos.title = 'Application démarrée';
        } else if (notif.type === 'STOP') {
            diplayInfos.icon = 'fa-thumbs-down icon-bg-red';
            diplayInfos.alert = 'alert-blocks-error';
            diplayInfos.color = 'color-red';
            diplayInfos.title = 'Application arrêtée';
        } else {
            diplayInfos.icon = 'fa-history icon-bg-blue';
            diplayInfos.alert = 'alert-blocks-info';
            diplayInfos.color = 'color-blue';
            diplayInfos.title = 'Nouvelle version';
        }
        return diplayInfos;
    }

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

    function findAll() {
        return $http.get('api/notification');
    }

    return {
        findAll: findAll,
        display: display
    };
});