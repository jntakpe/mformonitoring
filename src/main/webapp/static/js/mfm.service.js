mfmApp.factory('PagingService', PagingService);
mfmApp.factory('AlertService', AlertService);

function PagingService() {
    "use strict";

    function paginate(currentPage, numberPerPage, data) {
        var offset, page;
        page = data.length / numberPerPage;
        if (currentPage > page) {
            page = page < 1 ? 1 : Math.ceil(page);
            currentPage = page;
        }
        offset = (currentPage - 1) * numberPerPage;
        return {
            data: data.slice(offset, offset + numberPerPage),
            total: data.length
        };
    }

    function toListParams(data, numberPerPage) {
        var props = {};
        props.total = data.length;
        props.numberPerPage = numberPerPage;
        props.numPages = Math.ceil(props.total / numberPerPage);
        props.current = 1;
        return props;
    }

    function sortClass(column, sort) {
        if (column === sort.column) {
            return sort.reverse ? 'fa-sort-desc' : 'fa-sort-asc';
        }
        return 'fa-sort';
    }

    function sortColumn(column, sort) {
        if (sort.column === column) {
            if (sort.reverse) {
                sort.column = null;
                sort.reverse = false;
            } else {
                sort.reverse = true;
            }
        } else {
            sort.column = column;
            sort.reverse = false;
        }
        sort.class[column] = sortClass(column, sort);
    }

    return {
        paginate: paginate,
        toListParams: toListParams,
        sort: sortColumn
    };
}

function AlertService() {
    "use strict";

    function Alert(type, msg) {
        this.type = type;
        this.msg = msg;
        this.active = true;
    }

    return {
        success: function (msg) {
            return new Alert('success', msg);
        },
        error: function (msg) {
            return new Alert('error', msg);
        }
    };
}