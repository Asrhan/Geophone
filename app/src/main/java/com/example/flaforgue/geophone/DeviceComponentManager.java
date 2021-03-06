package com.example.flaforgue.geophone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Baptite_Portable on 29/11/2016.
 */

public class DeviceComponentManager implements LocationListener{

    private final LocationManager mLocationManager;
    private static Vibrator mVibrate;
    private static MediaPlayer mMediaPlayer;
    private static CameraManager mCameraManager = null;
    private static Context context = null;


    public DeviceComponentManager(Context c) {

        context = c;
        this.mLocationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        mVibrate = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        mMediaPlayer = MediaPlayer.create(context, R.raw.sound);
        mCameraManager = (CameraManager) this.context.getSystemService(Context.CAMERA_SERVICE);
    }

    public Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        boolean isGPSEnabled = false;
        boolean isNetworkEnabled = false;

        Location networkLocation = null;
        Location GPSLocation = null;
        Location finalLocation = null;


        isGPSEnabled = mLocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER);
        isNetworkEnabled = mLocationManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled ) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            GPSLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        if (isNetworkEnabled) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            networkLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        if(GPSLocation != null && networkLocation != null) {
            if (GPSLocation.getAccuracy() > networkLocation.getAccuracy()) {
                finalLocation = networkLocation;
                Toast.makeText(this.context, R.string.net_loc, Toast.LENGTH_LONG).show();
            } else {
                finalLocation = GPSLocation;
                Toast.makeText(this.context, R.string.gps_loc, Toast.LENGTH_LONG).show();
            }
        } else {
            if (GPSLocation != null) {
                finalLocation = GPSLocation;
                Toast.makeText(this.context, R.string.gps_loc, Toast.LENGTH_LONG).show();
            } else if (networkLocation != null) {
                finalLocation = networkLocation;
                Toast.makeText(this.context, R.string.net_loc, Toast.LENGTH_LONG).show();
            }
        }
        if (finalLocation == null)
            Toast.makeText(this.context, R.string.unk_loc, Toast.LENGTH_LONG).show();

        return finalLocation;

    }

    public static void doVibrate() {
        long[] pattern = {0,1000,500};
        mVibrate.vibrate(pattern,0);
    }

    public static void stopVibrate() {
        mVibrate.cancel();
    }

    public static void playSound() {
        mMediaPlayer.setVolume(1f,1f);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);
    }

    public static void stopSound() {
        mMediaPlayer.stop();
    }

    public static void turnOnFlash() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
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
                //TODO
            }
        }
    }

    public static void turnOffFlash() {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String cameraId = null;
                try {
                    cameraId = mCameraManager.getCameraIdList()[0];
                    mCameraManager.setTorchMode(cameraId, false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            else {
                //TODO
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
