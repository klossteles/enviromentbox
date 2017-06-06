(function() {
    'use strict';
    angular
        .module('enviromentBoxApp')
        .factory('TipoSensor', TipoSensor);

    TipoSensor.$inject = ['$resource'];

    function TipoSensor ($resource) {
        var resourceUrl =  'api/tipo-sensors/:id';

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
