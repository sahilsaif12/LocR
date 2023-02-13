package com.example.locr.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.locr.R;
import com.example.locr.User.SingleCategoryPlaces;
import com.example.locr.User.UserDashboard;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    GoogleMap myGoogleMap;
    TextView textView;
    FloatingActionButton fab;
    public static LatLng prev_latLng;
    private FusedLocationProviderClient mLocationClient;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    Double destination_lat=null,destination_lon=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        fab=findViewById(R.id.fab);

        initMap();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLocation(UserDashboard.lat,UserDashboard.lon);
//                getCurrLoc();


            }


        });

        destination_lat=getIntent().getDoubleExtra("lat",0);
        destination_lon=getIntent().getDoubleExtra("lon",0);
//        drawLine();




//        if (){
//        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    // Initialize Map on the activity
    private void initMap() {
            SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
            supportMapFragment.getMapAsync( this);
    }

    // Drawing a line between two distance(Latlon)
    private void drawLine() {
        LatLng start_location=new LatLng(UserDashboard.lat, UserDashboard.lon);
        LatLng end_location=new LatLng(destination_lat,destination_lon);

//        Log.d("latlon", String.valueOf(location));
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(start_location)
                .add(end_location)
                .color(0xFF3DBA43)
                .width(20.0F)

        ;
        myGoogleMap.addPolyline(polylineOptions);

        MarkerOptions starting_pos=new MarkerOptions();
        starting_pos.position(start_location)
                .icon(BitmapFromVector(getApplicationContext(),R.drawable.boy_icon));
        myGoogleMap.addMarker(starting_pos);
        CircleOptions starting_pos_circle=new CircleOptions();
        starting_pos_circle.center(start_location);
        starting_pos_circle.radius(75);
        starting_pos_circle.fillColor(0x9F9F9F9F);
        starting_pos_circle.strokeColor(0x00000000);
        myGoogleMap.addCircle(starting_pos_circle);

        MarkerOptions ending_pos=new MarkerOptions();
        ending_pos.position(end_location);
        myGoogleMap.addMarker(ending_pos);
        CircleOptions ending_pos_circle=new CircleOptions();
        ending_pos_circle.center(end_location);
        ending_pos_circle.radius(75);
        ending_pos_circle.fillColor(0x9F9F9F9F);
        ending_pos_circle.strokeColor(0x00000000);
        myGoogleMap.addCircle(ending_pos_circle);
        CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(start_location,16);
        myGoogleMap.moveCamera(cameraUpdate);
        myGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    // Adding a custom icon as location marker
    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    // Going to current location on map
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

        String act=getIntent().getStringExtra("activity");
        if (act.equals("singleCategoryPlaces")){
            drawLine();
        }
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