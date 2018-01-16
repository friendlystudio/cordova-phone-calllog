
var PhoneCallLog = {

    getCallLog: function(successCallback, errorCallback) {

        errorCallback = errorCallback || this.errorCallback;

        cordova.exec(successCallback, errorCallback, 'PhoneCallLog', 'getCallLog', []);

    }
};

module.exports = PhoneCallLog;
