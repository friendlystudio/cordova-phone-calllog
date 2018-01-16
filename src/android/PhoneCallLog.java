package com.friendlystudio.phonecalllog;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONArray;


public class PhoneCallLog extends CordovaPlugin {

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        return true;
    }

}