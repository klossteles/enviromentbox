(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('UsuarioDetailController', UsuarioDetailController);

    UsuarioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuario', 'Empresa', 'Cargo'];

    function UsuarioDetailController($scope, $rootScope, $stateParams, previousState, entity, Usuario, Empresa, Cargo) {
        var vm = this;

        vm.usuario = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('enviromentBoxApp:usuarioUpdate', function(event, result) {
            vm.usuario = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
