(function() {
    'use strict';
    angular
        .module('enviromentBoxApp')
        .factory('AlertaDevice', AlertaDevice);

    AlertaDevice.$inject = ['$resource', 'DateUtils'];

    function AlertaDevice ($resource, DateUtils) {
        var resourceUrl =  'api/alerta-devices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.data_hora = DateUtils.convertLocalDateFromServer(data.data_hora);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.data_hora = DateUtils.convertLocalDateToServer(copy.data_hora);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.data_hora = DateUtils.convertLocalDateToServer(copy.data_hora);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
