(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('TipoSensorController', TipoSensorController);

    TipoSensorController.$inject = ['TipoSensor'];

    function TipoSensorController(TipoSensor) {

        var vm = this;

        vm.tipoSensors = [];

        loadAll();

        function loadAll() {
            TipoSensor.query(function(result) {
                vm.tipoSensors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
