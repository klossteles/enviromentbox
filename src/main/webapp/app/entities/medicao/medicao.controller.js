(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('MedicaoController', MedicaoController);

    MedicaoController.$inject = ['Medicao'];

    function MedicaoController(Medicao) {

        var vm = this;

        vm.medicaos = [];

        loadAll();

        function loadAll() {
            Medicao.query(function(result) {
                vm.medicaos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
