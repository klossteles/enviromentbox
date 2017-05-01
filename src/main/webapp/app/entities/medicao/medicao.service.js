(function() {
    'use strict';
    angular
        .module('enviromentBoxApp')
        .factory('Medicao', Medicao);

    Medicao.$inject = ['$resource'];

    function Medicao ($resource) {
        var resourceUrl =  'api/medicaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
