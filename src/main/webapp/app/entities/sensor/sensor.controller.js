(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('SensorController', SensorController);

    SensorController.$inject = ['Sensor'];

    function SensorController(Sensor) {

        var vm = this;

        vm.sensors = [];

        loadAll();

        function loadAll() {
            Sensor.query(function(result) {
                vm.sensors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
