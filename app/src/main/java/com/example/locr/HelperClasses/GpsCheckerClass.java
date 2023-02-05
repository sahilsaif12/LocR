package com.example.locr.HelperClasses;

import android.content.Context;
import android.location.LocationManager;
import android.location.LocationRequest;

import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class GpsCheckerClass {
    Context context;
    SettingsClient mSettingsClient;
    LocationSettingsRequest mLocationSettingsRequest;
    LocationManager locationManager;
    LocationRequest locationRequest;

    public GpsCheckerClass(Context context) {
        this.context = context;
    }
}
