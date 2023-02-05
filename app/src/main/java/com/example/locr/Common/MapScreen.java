package com.example.locr.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locr.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    GoogleMap myGoogleMap;
    TextView textView;
    FloatingActionButton fab;
    public static LatLng prev_latLng;
    private FusedLocationProviderClient mLocationClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        fab=findViewById(R.id.fab);

        initMap();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrLoc();
            }


        });

    }

    private void initMap() {
//        if (isGpsEnable()) {
            SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
            supportMapFragment.getMapAsync( this);
//        }
    }

//    private boolean isGpsEnable(){
//        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
//        Boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if (providerEnable) {
//            return true;
//        }else {
////            turnOnGps();
//        }
//        return false;
//    }

//    private void turnOnGps() {
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setInterval(10000);
//            locationRequest.setFastestInterval(5000);
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//            SettingsClient client = LocationServices.getSettingsClient(this);
//            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//            task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
//                @Override
//                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
//                    // All location settings are satisfied. The client can initialize
//                    // location requests here.
//                    // ...
//                }
//            });
//
//            task.addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if (e instanceof ResolvableApiException) {
//                    // Location settings are not satisfied, but this can be fixed
//                    // by showing the user a dialog.
//                    try {
//                        // Show the dialog by calling startResolutionForResult(),
//                        // and check the result in onActivityResult().
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(MapScreen.this, REQUEST_CHECK_SETTINGS);
//                    } catch (IntentSender.SendIntentException sendEx) {
//                        // Ignore the error.
//                    }
//                }
//            }
//        });
//    }

    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        mLocationClient= LocationServices.getFusedLocationProviderClient(this);
        mLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            goToLocation(location.getLatitude(),location.getLongitude());
                        }
                        else {
                            Toast.makeText(MapScreen.this, "Something went wrong in accessing location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
//            if (task.isComplete()){

//            }
//        });
    }

    private void goToLocation(double latitude, double longitude) {
        LatLng latLng=new LatLng(latitude,longitude);
        if (latLng.equals(prev_latLng)){
            return;
        }
        prev_latLng=latLng;
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(latLng,18);
        myGoogleMap.moveCamera(cameraUpdate);
        myGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(latitude+" : "+longitude);
        myGoogleMap.addMarker(markerOptions);

        CircleOptions circleOptions=new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(75);
        circleOptions.fillColor(0x9F9F9F9F);
        circleOptions.strokeColor(0x00000000);
//        circleOptions.strokeWidth(2);
        myGoogleMap.addCircle(circleOptions);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myGoogleMap=googleMap;
//        myGoogleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}








//        mapView=findViewById(R.id.mapScreen);
//
//        mapView.getMapAsync( this);
//        mapView.onCreate(savedInstanceState);




//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }