(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('TipoSensorDialogController', TipoSensorDialogController);

    TipoSensorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoSensor'];

    function TipoSensorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoSensor) {
        var vm = this;

        vm.tipoSensor = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoSensor.id !== null) {
                TipoSensor.update(vm.tipoSensor, onSaveSuccess, onSaveError);
            } else {
                TipoSensor.save(vm.tipoSensor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('enviromentBoxApp:tipoSensorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
