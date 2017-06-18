(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alerta-device', {
            parent: 'entity',
            url: '/alerta-device',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlertaDevices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alerta-device/alerta-devices.html',
                    controller: 'AlertaDeviceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alerta-device-detail', {
            parent: 'alerta-device',
            url: '/alerta-device/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AlertaDevice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alerta-device/alerta-device-detail.html',
                    controller: 'AlertaDeviceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AlertaDevice', function($stateParams, AlertaDevice) {
                    return AlertaDevice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alerta-device',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alerta-device-detail.edit', {
            parent: 'alerta-device-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta-device/alerta-device-dialog.html',
                    controller: 'AlertaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlertaDevice', function(AlertaDevice) {
                            return AlertaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alerta-device.new', {
            parent: 'alerta-device',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta-device/alerta-device-dialog.html',
                    controller: 'AlertaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                data_hora: null,
                                processado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alerta-device', null, { reload: 'alerta-device' });
                }, function() {
                    $state.go('alerta-device');
                });
            }]
        })
        .state('alerta-device.edit', {
            parent: 'alerta-device',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta-device/alerta-device-dialog.html',
                    controller: 'AlertaDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AlertaDevice', function(AlertaDevice) {
                            return AlertaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alerta-device', null, { reload: 'alerta-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alerta-device.delete', {
            parent: 'alerta-device',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alerta-device/alerta-device-delete-dialog.html',
                    controller: 'AlertaDeviceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AlertaDevice', function(AlertaDevice) {
                            return AlertaDevice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alerta-device', null, { reload: 'alerta-device' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
