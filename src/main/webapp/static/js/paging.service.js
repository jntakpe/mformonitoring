mfmApp.factory('PageService', function () {
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
            page: currentPage,
            total: data.length
        };
    }

    function toListParams(data, numberPerPage) {
        var props = {};
        props.total = data.length;
        props.numberPerPage = numberPerPage;
        props.numPages = Math.ceil(props.total / numberPerPage);
        return props;
    }

    function sort(column, sort) {
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
    }

    function sortClass(column, sort) {
        if (sort.column === column) {
            return sort.reverse ? 'fa-sort-desc' : 'fa-sort-asc';
        }
        return 'fa-sort';
    }

    return {
        paginate: paginate,
        toListParams: toListParams,
        sort: sort,
        sortClass: sortClass
    };
});