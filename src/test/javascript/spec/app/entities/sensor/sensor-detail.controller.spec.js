'use strict';

describe('Controller Tests', function() {

    describe('Sensor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSensor, MockDevice, MockTipoSensor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSensor = jasmine.createSpy('MockSensor');
            MockDevice = jasmine.createSpy('MockDevice');
            MockTipoSensor = jasmine.createSpy('MockTipoSensor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sensor': MockSensor,
                'Device': MockDevice,
                'TipoSensor': MockTipoSensor
            };
            createController = function() {
                $injector.get('$controller')("SensorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'enviromentBoxApp:sensorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
