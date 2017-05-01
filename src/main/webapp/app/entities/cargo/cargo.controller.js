(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('CargoController', CargoController);

    CargoController.$inject = ['Cargo'];

    function CargoController(Cargo) {

        var vm = this;

        vm.cargos = [];

        loadAll();

        function loadAll() {
            Cargo.query(function(result) {
                vm.cargos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
