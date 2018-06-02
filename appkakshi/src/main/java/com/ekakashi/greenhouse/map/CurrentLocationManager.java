package com.ekakashi.greenhouse.map;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.ekakashi.greenhouse.R;

import java.lang.ref.WeakReference;


public class CurrentLocationManager implements LocationListener {

    /**
     * This class provides access to the system location services.
     */
    private LocationManager mLocationManager;
    /**
     * Activity context.
     */
    private WeakReference<Context> mWeakReferenceContext;
    /**
     * return current location.
     */
    private WeakReference<CurrentLocationCallback> mWeakReferenceLocationCallback;

    /**
     * Constructor.
     *
     * @param activityContext  : activity context.
     * @param locationCallback : The current location will be returned to the callback.
     */
    public CurrentLocationManager(Context activityContext, CurrentLocationCallback locationCallback) {
        mWeakReferenceContext = new WeakReference<>(activityContext);
        mWeakReferenceLocationCallback = new WeakReference<>(locationCallback);
    }

    /**
     * Enable GPS and requestLocationUpdates.
     */
    public final void requestLocationUpdates() {
        // don't start listeners if no provider is enabled
        if (mWeakReferenceContext.get() != null) {
            if (!isLocationEnabled(mWeakReferenceContext.get())) {
                requestEnableNetworkProvider(mWeakReferenceContext.get());
            } else {
                startRequestLocationUpdate(mWeakReferenceContext.get());
            }
        }
    }

    private void startRequestLocationUpdate(Context context) {

        /*Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);*/
        /* not use GPS, only use wireless network
         * String locationProvider =
         * mLocationManager.getBestProvider(criteria, true);
         */
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) context.getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);
        }
        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 5000, 0, this);
            } else {
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 0, this);
            }
        }

        /*Timer to get location from history when requestLocationUpdates
        don 't return result*/
        countDownUpdateLocation();
    }

    /**
     * The method must be called in onDestroy() of activity to
     * removeUpdateLocation and cancel CountDownTimer.
     */
    public final void stopRequestLocationUpdates() {
        if (mLocationManager != null) {
            if (mWeakReferenceContext.get() != null) {
                if (ActivityCompat.checkSelfPermission(mWeakReferenceContext.get(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.removeUpdates(this);
                }
            } else {
                //Context null or notNull, we will also remove location listener.
                mLocationManager.removeUpdates(this);
            }
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    /**
     * Show dialog to request enable network provider.
     */
    private void requestEnableNetworkProvider(final Context activityContext) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activityContext);
        alertDialogBuilder.setTitle("Permissions Required")
                .setMessage("You have forcefully denied some of the required permissions " +
                        "for this action. Please open settings, go to permissions and allow them.")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!isLocationEnabled(activityContext)) {
                            activityContext.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        } else {
                            startRequestLocationUpdate(activityContext);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setCancelable(false)
                .create()
                .show();
       /* Utilities.showCustomDialog(activityContext, 0, activityContext.getString(R.string.cannot_detect_current_location),
                activityContext.getString(R.string.enable_location),
                activityContext.getString(R.string.ok), null, new Runnable() {

                    @Override
                    public void run() {
                        // TODO click yes
                        if (!isLocationEnabled(activityContext)) {
                            activityContext.startActivity(new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        } else {
                            startRequestLocationUpdate(activityContext);
                        }
                    }
                }, null);
        */
    }

    private static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                Log.e("CurrentLocationManager", "isLocationEnabled", e);
            }
            return locationMode != Settings.Secure.LOCATION_MODE_SENSORS_ONLY && locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return (!TextUtils.isEmpty(locationProviders) && locationProviders.contains(LocationManager.GPS_PROVIDER));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mLocationManager != null) {
            stopRequestLocationUpdates();
            if (mWeakReferenceLocationCallback.get() != null) {
                mWeakReferenceLocationCallback.get().returnCurrentLocation(location);
            }
        }

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

    /**
     * The number of millis in the future from the call to start().
     * until the countdown is done and onFinish() is called.
     * <p/>
     * It is also the interval along the way to receive onTick(long) callbacks.
     **/
    private static final long TWENTY_SECS = 20000;
    /**
     * Timer to get location from history when requestLocationUpdates don't return result.
     */
    private static CountDownTimer mCountDownTimer;

    /**
     * Init CountDownTimer to to get location from history when requestLocationUpdates don't return result.
     */
    private void countDownUpdateLocation() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        mCountDownTimer = new CountDownTimer(TWENTY_SECS, TWENTY_SECS) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Location location = null;

                if (mWeakReferenceContext.get() != null && ActivityCompat.checkSelfPermission(mWeakReferenceContext.get(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    location = mLocationManager
                            .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }
                stopRequestLocationUpdates();
                if (mWeakReferenceLocationCallback.get() != null) {
                    mWeakReferenceLocationCallback.get().returnCurrentLocation(location);
                }
            }
        }.start();
    }
}
