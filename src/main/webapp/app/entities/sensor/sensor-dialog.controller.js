(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('SensorDialogController', SensorDialogController);

    SensorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sensor', 'Device', 'TipoSensor'];

    function SensorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sensor, Device, TipoSensor) {
        var vm = this;

        vm.sensor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query();
        vm.tiposensors = TipoSensor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.sensor.id !== null) {
                Sensor.update(vm.sensor, onSaveSuccess, onSaveError);
            } else {
                Sensor.save(vm.sensor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('enviromentBoxApp:sensorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
