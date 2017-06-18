(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('AlertaDeviceDeleteController',AlertaDeviceDeleteController);

    AlertaDeviceDeleteController.$inject = ['$uibModalInstance', 'entity', 'AlertaDevice'];

    function AlertaDeviceDeleteController($uibModalInstance, entity, AlertaDevice) {
        var vm = this;

        vm.alertaDevice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AlertaDevice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
