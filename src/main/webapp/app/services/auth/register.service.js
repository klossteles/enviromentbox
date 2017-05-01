(function () {
    'use strict';

    angular
        .module('enviromentBoxApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
