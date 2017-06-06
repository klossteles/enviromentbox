(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('TipoSensorDetailController', TipoSensorDetailController);

    TipoSensorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoSensor'];

    function TipoSensorDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoSensor) {
        var vm = this;

        vm.tipoSensor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('enviromentBoxApp:tipoSensorUpdate', function(event, result) {
            vm.tipoSensor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
