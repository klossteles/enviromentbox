(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('SensorDetailController', SensorDetailController);

    SensorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sensor', 'Device', 'TipoSensor'];

    function SensorDetailController($scope, $rootScope, $stateParams, previousState, entity, Sensor, Device, TipoSensor) {
        var vm = this;

        vm.sensor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('enviromentBoxApp:sensorUpdate', function(event, result) {
            vm.sensor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
