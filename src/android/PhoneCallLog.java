package com.friendlystudio.phonecalllog;

import org.apache.cordova.PermissionHelper;
import java.util.Date;
import android.database.Cursor;
import android.net.Uri;
import android.content.ContentResolver;
import java.util.TimeZone;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.provider.Settings;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;

public class PhoneCallLog extends CordovaPlugin {

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("getCallLog".equals(action)) {

            final PluginResult result = new PluginResult(PluginResult.Status.OK, this.getCallList());

            callbackContext.sendPluginResult(result);

        } else {

            return false;
        }

        return true;

    }

    public JSONArray getCallList() {

       /**
        * this.firstCall = 0;
        * this.lastCall = 20;
        */

        JSONArray res = new JSONArray();

        if(!PermissionHelper.hasPermission(this, Manifest.permission.READ_CALL_LOG)) {

            PermissionHelper.requestPermission(this,1,Manifest.permission.READ_CALL_LOG);

        } else {

            final Context context =  cordova.getActivity().getApplicationContext();

            res = this.getCallDetails(context);
        }

        return res;

    }

    /**
     * Get Call details
     *
     * @param context
     * @return
     */

    private JSONArray getCallDetails(Context context) {

        StringBuffer sb = new StringBuffer();
        ContentResolver cr = context.getContentResolver();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor managedCursor = cr.query(callUri, null, null, null, strOrder);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int callerIndex = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        JSONArray calls = new JSONArray();

        while (managedCursor.moveToNext()) {

            JSONObject call = new JSONObject();

            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            String prettyDate = callDate;
            String caller = managedCursor.getString(callerIndex);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);

            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            try {

                call.put("phoneNumber", phNumber);
                call.put("date", prettyDate);
                call.put("type", dir);
                call.put("caller", caller);
                call.put("callDuration", callDuration);
                calls.put(call);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        managedCursor.close();

        return calls;

    }

}