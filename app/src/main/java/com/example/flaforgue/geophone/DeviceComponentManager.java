package com.example.flaforgue.geophone;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;

/**
 * Created by Baptite_Portable on 29/11/2016.
 */

public class DeviceComponentManager extends Service {

    IBinder mBinder = (IBinder) new DeviceComponentManager.LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public DeviceComponentManager getDeviceComponentManagerInstance() {
            return DeviceComponentManager.this;
        }
    }

    public void turnOnFlash() {
        CameraManager mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String cameraId = null;
            try {
                cameraId = mCameraManager.getCameraIdList()[0];
                mCameraManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        else {

        }
    }

}
