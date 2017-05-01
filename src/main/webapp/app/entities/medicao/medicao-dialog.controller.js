(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('MedicaoDialogController', MedicaoDialogController);

    MedicaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medicao', 'Sensor', 'Device'];

    function MedicaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medicao, Sensor, Device) {
        var vm = this;

        vm.medicao = entity;
        vm.clear = clear;
        vm.save = save;
        vm.sensors = Sensor.query();
        vm.devices = Device.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medicao.id !== null) {
                Medicao.update(vm.medicao, onSaveSuccess, onSaveError);
            } else {
                Medicao.save(vm.medicao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('enviromentBoxApp:medicaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
