'use strict';

describe('Controller Tests', function() {

    describe('Medicao Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMedicao, MockSensor, MockDevice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMedicao = jasmine.createSpy('MockMedicao');
            MockSensor = jasmine.createSpy('MockSensor');
            MockDevice = jasmine.createSpy('MockDevice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Medicao': MockMedicao,
                'Sensor': MockSensor,
                'Device': MockDevice
            };
            createController = function() {
                $injector.get('$controller')("MedicaoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'enviromentBoxApp:medicaoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
