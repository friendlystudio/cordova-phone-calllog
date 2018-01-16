

var PhoneCallLog = {

    getCallLog: function(successCallback) {

        cordova.exec(successCallback, null, 'PhoneCallLog', 'getCallLog', []);
    }
};

module.exports = PhoneCallLog;
