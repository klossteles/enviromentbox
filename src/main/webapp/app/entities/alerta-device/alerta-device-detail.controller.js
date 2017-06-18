(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('AlertaDeviceDetailController', AlertaDeviceDetailController);

    AlertaDeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AlertaDevice', 'Device', 'Sensor'];

    function AlertaDeviceDetailController($scope, $rootScope, $stateParams, previousState, entity, AlertaDevice, Device, Sensor) {
        var vm = this;

        vm.alertaDevice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('enviromentBoxApp:alertaDeviceUpdate', function(event, result) {
            vm.alertaDevice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
