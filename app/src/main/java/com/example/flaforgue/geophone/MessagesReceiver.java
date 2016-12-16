package com.example.flaforgue.geophone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessagesReceiver extends BroadcastReceiver {

    private final SmsManager smsManager = SmsManager.getDefault();
    private final String   ACTION_RECEIVE_SMS  = "android.provider.Telephony.SMS_RECEIVED";

    private DeviceComponentSwitch deviceComponentSwitch;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(ACTION_RECEIVE_SMS)) {
            this.deviceComponentSwitch = new DeviceComponentSwitch(context);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for (int i = 0; i < pdus.length; i++)  {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], bundle.getString("format"));
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                }

                if (messages.length >= 0) {
                    final String messageBody = messages[0].getMessageBody();
                    final String phoneNumber = messages[0].getDisplayOriginatingAddress();

                    this.handleRequest(context, phoneNumber, messageBody);
                }
            }
        }

    }

    private void handleRequest(Context context, String phoneNumber, String messageBody) {
        switch (messageBody.split(";")[0]) {

            case MessageCode.LOCATION_REQUEST:
                Location currentLocation = deviceComponentSwitch.getLocation();
                if (!checkCoordinates(currentLocation)) {
                    this.smsManager.sendTextMessage(phoneNumber, null, MessageCode.UNKNOWN_LOCATION + ";", null, null);
                } else {
                    this.smsManager.sendTextMessage(phoneNumber, null, MessageCode.SEND_LOCATION + ";" + currentLocation.getLongitude() + ";" + currentLocation.getLatitude(), null, null);
                    this.deviceComponentSwitch.turnOnFlash();
                    this.deviceComponentSwitch.doVibrate();
                    this.deviceComponentSwitch.playSound();
                    Intent intentFound = new Intent(context, NearActivity.class);
                    intentFound.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentFound);
                }
                break;

            case MessageCode.SEND_LOCATION:
                Intent intentLocation = new Intent(context, MapsActivity.class);
                intentLocation.putExtra("longitude", messageBody.split(";")[1]);
                intentLocation.putExtra("latitude", messageBody.split(";")[2]);
                intentLocation.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentLocation);
                break;

            case MessageCode.UNKNOWN_LOCATION:
                Intent intentMenu = new Intent(context, HomeActivity.class);
                intentMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentMenu);
                break;

            default:
                Log.i("info", "" + messageBody.length());
                break;
        }
    }

    private boolean checkCoordinates (Location loc) {
        return (loc != null);
    }
}
