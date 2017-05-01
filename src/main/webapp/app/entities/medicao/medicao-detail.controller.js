(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .controller('MedicaoDetailController', MedicaoDetailController);

    MedicaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medicao', 'Sensor', 'Device'];

    function MedicaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Medicao, Sensor, Device) {
        var vm = this;

        vm.medicao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('enviromentBoxApp:medicaoUpdate', function(event, result) {
            vm.medicao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
