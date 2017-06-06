(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-sensor', {
            parent: 'entity',
            url: '/tipo-sensor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoSensors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensors.html',
                    controller: 'TipoSensorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tipo-sensor-detail', {
            parent: 'tipo-sensor',
            url: '/tipo-sensor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TipoSensor'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensor-detail.html',
                    controller: 'TipoSensorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TipoSensor', function($stateParams, TipoSensor) {
                    return TipoSensor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-sensor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-sensor-detail.edit', {
            parent: 'tipo-sensor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensor-dialog.html',
                    controller: 'TipoSensorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoSensor', function(TipoSensor) {
                            return TipoSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-sensor.new', {
            parent: 'tipo-sensor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensor-dialog.html',
                    controller: 'TipoSensorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-sensor', null, { reload: 'tipo-sensor' });
                }, function() {
                    $state.go('tipo-sensor');
                });
            }]
        })
        .state('tipo-sensor.edit', {
            parent: 'tipo-sensor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensor-dialog.html',
                    controller: 'TipoSensorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoSensor', function(TipoSensor) {
                            return TipoSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-sensor', null, { reload: 'tipo-sensor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-sensor.delete', {
            parent: 'tipo-sensor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-sensor/tipo-sensor-delete-dialog.html',
                    controller: 'TipoSensorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoSensor', function(TipoSensor) {
                            return TipoSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-sensor', null, { reload: 'tipo-sensor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
