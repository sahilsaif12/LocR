package com.example.locr.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.locr.Common.MainActivity;
import com.example.locr.Common.MapScreen;
import com.example.locr.HelperClasses.GpsChecker;
import com.example.locr.HelperClasses.HomeAdapter.CategoryAdapter;
import com.example.locr.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.example.locr.HelperClasses.HomeAdapter.MostViewedAdapter;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.CategoriesHelperClass;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.FeaturedHelperClass;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.MostViewedHelperClass;
import com.example.locr.R;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE=0.7f;
    public static Boolean isLocationPermissionGranted;
    RecyclerView featured_recycler,most_viewed_recycler,category_recycler;
    RecyclerView.Adapter adapter;
    GradientDrawable gradient1,gradient2,gradient3,gradient4,gradient5,gradient6,gradient7,gradient8;
    ImageView three_line_menu,add_location_icon;
    RelativeLayout relativeLayout3;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        checkMyPermission();

//        getSupportActionBar().hide();

        featured_recycler=findViewById(R.id.featured_recycler);
        most_viewed_recycler=findViewById(R.id.most_viewed_recycler);
        category_recycler=findViewById(R.id.category_recycler);
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
                    startActivity(intent);

                }
            }
        });
    }

    private boolean isGpsEnable(){
        LocationManager locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        Boolean providerEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnable) {
            return true;
        }else {
            GpsChecker gpsChecker=new GpsChecker();
            gpsChecker.turnOnGps(this, UserDashboard.this);
        }
        return false;
    }
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
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

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
    // Recycler views funcitons
    private void categoryRecycler() {

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xffDEB55D, 0xFFC5BDBD});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xff8FAF4D, 0xFFC5BDBD});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFFAA2A2, 0xFFC5BDBD});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFA2FAE8, 0xFFC5BDBD});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFF8CA4FB, 0xFFC5BDBD});
        gradient6 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x7C6BFF02, 0xFFC5BDBD});
        gradient7 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFFB9D8C, 0xFFC5BDBD});
        gradient8 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x92FFDE03, 0xFFC5BDBD});

        category_recycler.setHasFixedSize(true);
        category_recycler.setLayoutManager(new LinearLayoutManager (this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<CategoriesHelperClass> category= new ArrayList<>();
        category.add(new CategoriesHelperClass(gradient1,R.drawable.restaurent_category,"Restaurent"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.cinema_category,"Cinemall Hall"));
        category.add(new CategoriesHelperClass(gradient3,R.drawable.petrol_category,"Petrol Pump"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.education_category,"Institutions"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.hotel_category,"Hotels"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.train_staion_category,"Train Station"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.beach_category,"Beach"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.shop_category,"Shops"));
        adapter=new CategoryAdapter(category);
        category_recycler.setAdapter(adapter);
    }

    private void featuredRecycler() {
        featured_recycler.setHasFixedSize(true);
        featured_recycler.setLayoutManager(new LinearLayoutManager (this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations= new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.demo_img1,"Carnival Cinemas","best cinema hall for all language movies with resonable price"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.demo_img2,"Nico park","For spending fun time with friends or family or special ones , its the best"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.demo_img3,"Howrah Bridge","best and iconic place to visit in kolkata"));

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}