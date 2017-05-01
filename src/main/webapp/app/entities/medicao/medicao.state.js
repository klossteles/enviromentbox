(function() {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medicao', {
            parent: 'entity',
            url: '/medicao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medicaos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicao/medicaos.html',
                    controller: 'MedicaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('medicao-detail', {
            parent: 'medicao',
            url: '/medicao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medicao'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicao/medicao-detail.html',
                    controller: 'MedicaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Medicao', function($stateParams, Medicao) {
                    return Medicao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medicao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medicao-detail.edit', {
            parent: 'medicao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicao/medicao-dialog.html',
                    controller: 'MedicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicao', function(Medicao) {
                            return Medicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicao.new', {
            parent: 'medicao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicao/medicao-dialog.html',
                    controller: 'MedicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medicao', null, { reload: 'medicao' });
                }, function() {
                    $state.go('medicao');
                });
            }]
        })
        .state('medicao.edit', {
            parent: 'medicao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicao/medicao-dialog.html',
                    controller: 'MedicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicao', function(Medicao) {
                            return Medicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicao', null, { reload: 'medicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicao.delete', {
            parent: 'medicao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicao/medicao-delete-dialog.html',
                    controller: 'MedicaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medicao', function(Medicao) {
                            return Medicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicao', null, { reload: 'medicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
