(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('sensor', {
            parent: 'entity',
            url: '/sensor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sensors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensor/sensors.html',
                    controller: 'SensorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('sensor-detail', {
            parent: 'sensor',
            url: '/sensor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Sensor'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/sensor/sensor-detail.html',
                    controller: 'SensorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Sensor', function($stateParams, Sensor) {
                    return Sensor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'sensor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('sensor-detail.edit', {
            parent: 'sensor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor/sensor-dialog.html',
                    controller: 'SensorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sensor', function(Sensor) {
                            return Sensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sensor.new', {
            parent: 'sensor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor/sensor-dialog.html',
                    controller: 'SensorDialogController',
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
                    $state.go('sensor', null, { reload: 'sensor' });
                }, function() {
                    $state.go('sensor');
                });
            }]
        })
        .state('sensor.edit', {
            parent: 'sensor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor/sensor-dialog.html',
                    controller: 'SensorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Sensor', function(Sensor) {
                            return Sensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensor', null, { reload: 'sensor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('sensor.delete', {
            parent: 'sensor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/sensor/sensor-delete-dialog.html',
                    controller: 'SensorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Sensor', function(Sensor) {
                            return Sensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('sensor', null, { reload: 'sensor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
