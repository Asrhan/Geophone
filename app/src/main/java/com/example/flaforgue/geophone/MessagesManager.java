package com.example.flaforgue.geophone;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MessagesManager extends Service {

    // managers
    private final SmsManager messagesSender = SmsManager.getDefault();
    private MessagesReceiver messagesReceiver = null;
    private DeviceComponentSwitch deviceComponentSwitch = null;

    IBinder mBinder = (IBinder) new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        if(messagesReceiver == null) {
            messagesReceiver = new MessagesReceiver();
        }

        if(deviceComponentSwitch == null) {
            deviceComponentSwitch = new DeviceComponentSwitch(this);
        }

        return mBinder;
    }

    public class LocalBinder extends Binder {
        public MessagesManager getMessagesManagerInstance() {
            return MessagesManager.this;
        }
    }

    public void sendLocationRequest(String destination) {
        Location currentLocation = deviceComponentSwitch.getLocation();
        if (currentLocation != null) {
            this.messagesSender.sendTextMessage(destination, null, MessageCode.LOCATION_REQUEST + ";" + currentLocation.getLongitude() + ";" + currentLocation.getLatitude(), null, null);
            Toast.makeText(this, getString(R.string.request_sent), Toast.LENGTH_SHORT).show();
        }
    }


}
