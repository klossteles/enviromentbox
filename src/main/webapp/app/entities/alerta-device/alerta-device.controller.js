(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('AlertaDeviceController', AlertaDeviceController);

    AlertaDeviceController.$inject = ['AlertaDevice'];

    function AlertaDeviceController(AlertaDevice) {

        var vm = this;

        vm.alertaDevices = [];

        loadAll();

        function loadAll() {
            AlertaDevice.query(function(result) {
                vm.alertaDevices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
