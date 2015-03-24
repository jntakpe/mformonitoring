mfmApp.factory('PagingService', PagingService);
mfmApp.factory('AlertService', AlertService);

function PagingService($filter) {
    "use strict";

    function paginate(props, data) {
        var offset, page, numberPerPage = props.numberPerPage, currentPage = props.current, paginated;
        page = data.length / numberPerPage;
        if (currentPage > page) {
            page = page < 1 ? 1 : Math.ceil(page);
            currentPage = page;
        }
        offset = (currentPage - 1) * numberPerPage;
        paginated = data.slice(offset, offset + numberPerPage);
        props.total = paginated.length;
        return paginated;
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

    function process(data, search, sort, props) {
        var filtered, sorted;
        filtered = $filter('filter')(data, search);
        sorted = $filter('orderBy')(filtered, sort.column, sort.reverse);
        return paginate(props, sorted);
    }

    return {
        paginate: paginate,
        toListParams: toListParams,
        sort: sortColumn,
        process: process
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