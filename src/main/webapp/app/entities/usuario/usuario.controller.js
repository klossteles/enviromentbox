(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('UsuarioController', UsuarioController);

    UsuarioController.$inject = ['Usuario'];

    function UsuarioController(Usuario) {

        var vm = this;

        vm.usuarios = [];

        loadAll();

        function loadAll() {
            Usuario.query(function(result) {
                vm.usuarios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
