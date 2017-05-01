(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('EmpresaController', EmpresaController);

    EmpresaController.$inject = ['Empresa'];

    function EmpresaController(Empresa) {

        var vm = this;

        vm.empresas = [];

        loadAll();

        function loadAll() {
            Empresa.query(function(result) {
                vm.empresas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
