package com.example.locr.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.locr.HelperClasses.CategoryViewInterface;
import com.example.locr.HelperClasses.FetchingData;
import com.example.locr.HelperClasses.HomeAdapter.CategoryAdapter;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.CategoriesHelperClass;
import com.example.locr.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AllCategories extends AppCompatActivity implements CategoryViewInterface {

    RecyclerView.Adapter adapter;
    RecyclerView all_category_recycler;
    RelativeLayout loading_screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        all_category_recycler=findViewById(R.id.all_category_recycler);
        loading_screen=findViewById(R.id.all_category_loading_screen);
        loading_screen.setVisibility(View.GONE);
        allCategoryRecycler();
    }

    public void backBtn() {
        super.onBackPressed();
    }

// All category recycler view setter
    private void allCategoryRecycler() {

        GradientDrawable gradient1, gradient2,gradient3,gradient4,gradient5,gradient6,gradient7,gradient8;

        gradient1 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xffDEB55D, 0xFFC5BDBD});
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xff8FAF4D, 0xFFC5BDBD});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFFFAA2A2, 0xFFC5BDBD});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFF8CA4FB, 0xFFC5BDBD});
        gradient5 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xCBD51E6D, 0xFFC5BDBD});
        gradient6 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x7C6BFF02, 0xFFC5BDBD});
        gradient7 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0xFF913175, 0xFFC5BDBD});
        gradient8 = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{0x92FFDE03, 0xFFC5BDBD});

        gradient1.setCornerRadius(20.0F);
        gradient2.setCornerRadius(20.0F);
        gradient3.setCornerRadius(20.0F);
        gradient4.setCornerRadius(20.0F);
        gradient5.setCornerRadius(20.0F);
        gradient6.setCornerRadius(20.0F);
        gradient7.setCornerRadius(20.0F);
        gradient8.setCornerRadius(20.0F);

        all_category_recycler.setHasFixedSize(true);
        all_category_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ArrayList<CategoriesHelperClass> category= new ArrayList<>();
        category.add(new CategoriesHelperClass(gradient1,R.drawable.restaurent_category,"Restaurent","catering.restaurant"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.police_station_category,"Police Station","service.police"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.cinema_category,"Cinemall Hall","entertainment.cinema"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.hospital_category,"Hospital","healthcare.hospital"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.school_category,"School","education.school"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.hotel_category,"Hotels","accommodation.hotel"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.train_staion_category,"Train Station","public_transport.train"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.beach_category,"Beach","beach"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.mountain_category,"Mountain","natural.mountain"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.shopping_mall_category,"Shopping Mall","commercial.shopping_mall"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.college_category,"College","education.college"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.park_category,"Park","leisure.park"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.playground_category,"Playground","leisure.playground"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.pharmacy_category,"Pharmacy","healthcare.pharmacy"));
        category.add(new CategoriesHelperClass(gradient3,R.drawable.parking_category,"Parking","parking"));
        category.add(new CategoriesHelperClass(gradient1,R.drawable.ticket_category,"Booking Tickets","commercial.tickets_and_lottery"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.book_shop_category,"Books Shop","commercial.books"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.sweet_shop_category,"Sweet Shop","commercial.food_and_drink.confectionery"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.coffee_category,"Tea Or Coffee","commercial.food_and_drink.coffee_and_tea"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.drink_category,"Drink","commercial.food_and_drink.drinks"));
        category.add(new CategoriesHelperClass(gradient3,R.drawable.supermarket_category,"Supermarket","commercial.supermarket"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.electrotic_category,"Electronics","commercial.elektronics"));
        category.add(new CategoriesHelperClass(gradient1,R.drawable.hardware_category,"Hardware shop","commercial.houseware_and_hardware"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.jewelry_category,"Jewelry","commercial.jewelry"));
        category.add(new CategoriesHelperClass(gradient5,R.drawable.cafe_category,"Cafe","catering.cafe"));
        category.add(new CategoriesHelperClass(gradient7,R.drawable.pub_category,"Pub","catering.pub"));
        category.add(new CategoriesHelperClass(gradient3,R.drawable.guest_house_category,"Guest House","accommodation.guest_house"));
        category.add(new CategoriesHelperClass(gradient1,R.drawable.hostel_category,"Hostel","accommodation.hostel"));
        category.add(new CategoriesHelperClass(gradient8,R.drawable.museum_category,"Museum","entertainment.museum"));
        category.add(new CategoriesHelperClass(gradient2,R.drawable.furnitures_category,"Furniture","commercial.furniture_and_interior"));
        category.add(new CategoriesHelperClass(gradient4,R.drawable.water_park_category,"Water Park","entertainment.water_park"));
        category.add(new CategoriesHelperClass(gradient6,R.drawable.administrative_category,"Administrative","administrative"));
        adapter=new CategoryAdapter(category,this);
        all_category_recycler.setAdapter(adapter);
    }


// open Locations page of desired category
    @Override
    public void onItemClick(int position,ArrayList<CategoriesHelperClass> category) {
        loading_screen.setVisibility(View.VISIBLE);
        FetchingData fetchingData=new FetchingData();
        try {
            fetchingData.getLocationData(this,5000,category.get(position).getId());

//            Log.d("data", String.valueOf(data));
        } catch (IOException e) {
            e.printStackTrace();
        }


//                    loading_screen.setVisibility(View.GONE);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                    Log.d("msg", "running");
                if (UserDashboard.data!=null) {

                    Log.d("msg", "opening");
                    Intent intent = new Intent(getApplicationContext(), SingleCategoryPlaces.class);
                    intent.putExtra("id", category.get(position).getId());
                    intent.putExtra("img", category.get(position).getImage());
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
