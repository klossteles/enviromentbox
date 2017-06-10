(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('TipoSensorDeleteController',TipoSensorDeleteController);

    TipoSensorDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoSensor'];

    function TipoSensorDeleteController($uibModalInstance, entity, TipoSensor) {
        var vm = this;

        vm.tipoSensor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoSensor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
