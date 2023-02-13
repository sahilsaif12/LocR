package com.example.locr.User;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.Common.MapScreen;
import com.example.locr.HelperClasses.CategoryViewInterface;
import com.example.locr.HelperClasses.FetchingData;
import com.example.locr.HelperClasses.GpsChecker;
import com.example.locr.HelperClasses.HomeAdapter.CategoryAdapter;
import com.example.locr.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.locr.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.CategoriesHelperClass;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.FeaturedHelperClass;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.MostViewedHelperClass;
import com.example.locr.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CategoryViewInterface {

    static final float END_SCALE=0.7f;
    public static double lat,lon;
    public static JSONArray data;
    public static Boolean isLocationPermissionGranted;
    RecyclerView featured_recycler,most_viewed_recycler,category_recycler;
    RecyclerView.Adapter adapter;
    GradientDrawable gradient1,gradient2,gradient3,gradient4,gradient5,gradient6,gradient7,gradient8;
    ImageView three_line_menu,add_location_icon;
    RelativeLayout relativeLayout3;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;
    TextView viewAllCategory;
    RelativeLayout loading_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        checkMyPermission();

//        getSupportActionBar().hide();

        featured_recycler=findViewById(R.id.featured_recycler);
        most_viewed_recycler=findViewById(R.id.most_viewed_recycler);
        category_recycler=findViewById(R.id.category_recycler);
        loading_screen=findViewById(R.id.user_dash_loading_screen);
        featuredRecycler();
        mostViewedRecycler();
        categoryRecycler();

        //menu hooks
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        three_line_menu=findViewById(R.id.three_line_menu);
        contentView=findViewById(R.id.content);
        navigationDrawer();
        animateNavigationDrawer();



        relativeLayout3=findViewById(R.id.relativeLayout3);

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLocationPermissionGranted && isGpsEnable()){
                    Intent intent=new Intent(getApplicationContext(), MapScreen.class);
                    intent.putExtra("activity","userDashboard");
                    startActivity(intent);

                }
            }
        });

        // view all category
        viewAllCategory=findViewById(R.id.view_all_category);
        viewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AllCategories.class));
            }
        });

    }


    // Actions while highlighted category buttons clicked on home page
    public void highlightedCategoryClicked(View view){
        int id=view.getId();
        switch (id){
            case R.id.hotel_icon:{
                fetchCategoryLocations("accommodation.hotel",R.drawable.hotel_icon);
                break;
            }
            case R.id.restaurent_icon:{
                fetchCategoryLocations("catering.restaurant",R.drawable.restaurant_icon);
                break;
            }
            case R.id.education_icon:{
                fetchCategoryLocations("education",R.drawable.education_icon);
                break;
            }
            case R.id.shops_icon:{
                fetchCategoryLocations("commercial.shopping_mall",R.drawable.shop_icon);
                break;
            }


        }

    }

    // Checking if GPS is enabled or not. If not then it's enable gps
    private boolean isGpsEnable(){
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            getCurrentLatLon();
            return true;
        }else {
            GpsChecker gpsChecker=new GpsChecker();
            gpsChecker.turnOnGps(this, UserDashboard.this);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() { // Function runs every MINUTES minutes.
                    Log.d("k","running");
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getCurrentLatLon();
                        timer.cancel();
                    }
                }
            }, 0, 500);


        }
        return false;
    }

    // Getting the user's current location's lat lon
    @SuppressLint("MissingPermission")
    public void getCurrentLatLon() {
        FusedLocationProviderClient mLocationClient= LocationServices.getFusedLocationProviderClient(this);
        CancellationTokenSource cancellationTokenSource=new CancellationTokenSource();
        mLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,cancellationTokenSource.getToken()).addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    lat=location.getLatitude();
                    lon=location.getLongitude();
                    Log.d("latlon",lat+" : "+lon);
                }
            }
        });
    }

    // Taking Location permissions
    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isLocationPermissionGranted=true;
                isGpsEnable();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent= new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri= Uri.fromParts("package",getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    // Navigation Drawer functions
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        three_line_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    // Animation of navigation sidebar while opening and closing
    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.fade_color));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                final float diffScaledOffset=slideOffset * (1- END_SCALE );
                final float offsetScale=1-diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                final float xOffset=drawerView.getWidth()*slideOffset;
                final float xOffsetDiff=contentView.getWidth()*diffScaledOffset/2;
                final float xTranslation=xOffset-xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    // while sidebar is opened if back button pressed it close the sidebar
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    // ----- Recycler views funcitons -----


    private void categoryRecycler() {

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xffDEB55D, 0xFFC5BDBD});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xff8FAF4D, 0xFFC5BDBD});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFFAA2A2, 0xFFC5BDBD});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFF8CA4FB, 0xFFC5BDBD});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xCBD51E6D, 0xFFC5BDBD});
        gradient6 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x7C6BFF02, 0xFFC5BDBD});
        gradient7 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFFB9D8C, 0xFFC5BDBD});
        gradient8 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x92FFDE03, 0xFFC5BDBD});

        gradient1.setCornerRadius(20.0F);
        gradient2.setCornerRadius(20.0F);
        gradient3.setCornerRadius(20.0F);
        gradient4.setCornerRadius(20.0F);
        gradient5.setCornerRadius(20.0F);
        gradient6.setCornerRadius(20.0F);
        gradient7.setCornerRadius(20.0F);
        gradient8.setCornerRadius(20.0F);

        category_recycler.setHasFixedSize(true);
        category_recycler.setLayoutManager(new LinearLayoutManager (this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<CategoriesHelperClass> category= new ArrayList<>();
        category.add(new CategoriesHelperClass(gradient1,R.drawable.restaurent_category,"Restaurent","catering.restaurant"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.cinema_category,"Cinemall Hall","entertainment.cinema"));
        category.add(new CategoriesHelperClass(gradient3,R.drawable.hospital_category,"Hospital","healthcare.hospital"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.train_staion_category,"Train Station","public_transport.train"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.police_station_category,"Police Station","service.police"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.hotel_category,"Hotels","accommodation.hotel"));

        category.add(new CategoriesHelperClass(gradient7,R.drawable.coffee_category,"Tea Or Coffee","commercial.food_and_drink.coffee_and_tea"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.park_category,"Park","leisure.park"));

        adapter=new CategoryAdapter(category,this);
        category_recycler.setAdapter(adapter);
    }

    private void featuredRecycler() {
        featured_recycler.setHasFixedSize(true);
        featured_recycler.setLayoutManager(new LinearLayoutManager (this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations= new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/Sealdah_Railway_Station_-_Kolkata_2011-10-03_030250.JPG/400px-Sealdah_Railway_Station_-_Kolkata_2011-10-03_030250.JPG","Carnival Cinemas","best cinema hall for all language movies with resonable price"));
        featuredLocations.add(new FeaturedHelperClass("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Howrah_bridge_at_night.jpg/400px-Howrah_bridge_at_night.jpg","Nico park","For spending fun time with friends or family or special ones , its the best"));
//        featuredLocations.add(new FeaturedHelperClass(R.drawable.demo_img3,"Howrah Bridge","best and iconic place to visit in kolkata"));

        adapter=new FeaturedAdapter(featuredLocations);
        featured_recycler.setAdapter(adapter);
    }

    private void mostViewedRecycler() {

        most_viewed_recycler.setHasFixedSize(true);
        most_viewed_recycler.setLayoutManager(new LinearLayoutManager (this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MostViewedHelperClass> mostViewedLocations= new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.demo_img1,"Carnival Cinemas","best cinema hall for all language movies with resonable price"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.demo_img2,"Nico park","For spending fun time with friends or family or special ones , its the best"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.demo_img3,"Howrah Bridge","best and iconic place to visit in kolkata"));

        adapter=new MostViewedAdapter(mostViewedLocations);
        most_viewed_recycler.setAdapter(adapter);
    }


    // Actions while any item on navigation sidebar clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_all_category:{
                startActivity(new Intent(getApplicationContext(),AllCategories.class));
            }
            case R.id.nav_hotels:{
                drawerLayout.closeDrawer(GravityCompat.START);
                fetchCategoryLocations("accommodation.hotel",R.drawable.hotel_icon);
                break;
            }
            case R.id.nav_restaurent:{
                drawerLayout.closeDrawer(GravityCompat.START);
                fetchCategoryLocations("catering.restaurant",R.drawable.restaurant_icon);
                break;
            }
            case R.id.nav_education:{
                drawerLayout.closeDrawer(GravityCompat.START);
                fetchCategoryLocations("education",R.drawable.education_icon);
                break;
            }
            case R.id.nav_shops:{
                drawerLayout.closeDrawer(GravityCompat.START);
                fetchCategoryLocations("commercial.shopping_mall",R.drawable.shop_icon);
                break;
            }
        }
        return true;
    }

    // While clicking on any category view
    @Override
    public void onItemClick(int position,ArrayList<CategoriesHelperClass> category) {
        String category_id=category.get(position).getId();
        int category_img=category.get(position).getImage();
        fetchCategoryLocations(category_id,category_img);


    }

    // Fetching the location data of the desirable category
    private void fetchCategoryLocations(String id,int img) {
        loading_screen.setVisibility(View.VISIBLE);
        FetchingData fetchingData=new FetchingData();
        try {
            fetchingData.getLocationData(this,5000,id);
//            Log.d("data", String.valueOf(data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                Log.d("k","running");
                if (UserDashboard.data!=null) {
                    Log.d("task","not null");
//                    loading_screen.setVisibility(View.GONE);
                    Intent intent=new Intent(getApplicationContext(),SingleCategoryPlaces.class);
                    intent.putExtra("id", id);
                    intent.putExtra("img", img);
                    startActivity(intent);
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected void onResume() {
        loading_screen.setVisibility(View.GONE);
        super.onResume();
    }
}