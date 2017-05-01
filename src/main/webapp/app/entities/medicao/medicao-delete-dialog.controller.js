(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('MedicaoDeleteController',MedicaoDeleteController);

    MedicaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medicao'];

    function MedicaoDeleteController($uibModalInstance, entity, Medicao) {
        var vm = this;

        vm.medicao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medicao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
