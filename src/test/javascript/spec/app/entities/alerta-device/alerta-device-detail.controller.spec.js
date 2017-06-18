'use strict';

describe('Controller Tests', function() {

    describe('AlertaDevice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlertaDevice, MockDevice, MockSensor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlertaDevice = jasmine.createSpy('MockAlertaDevice');
            MockDevice = jasmine.createSpy('MockDevice');
            MockSensor = jasmine.createSpy('MockSensor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AlertaDevice': MockAlertaDevice,
                'Device': MockDevice,
                'Sensor': MockSensor
            };
            createController = function() {
                $injector.get('$controller')("AlertaDeviceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'enviromentBoxApp:alertaDeviceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
