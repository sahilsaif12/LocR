package com.example.locr.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.locr.Common.MapScreen;
import com.example.locr.HelperClasses.DirectionViewInterface;
import com.example.locr.HelperClasses.FetchingData;
import com.example.locr.HelperClasses.HomeAdapter.LocationAdapter;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.LocationHelperClass;
import com.example.locr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SingleCategoryPlaces extends AppCompatActivity implements DirectionViewInterface {

    RecyclerView location_recycler;
    RecyclerView.Adapter adapter;
    Double lat,lon;
    TextView radius_text;
    SeekBar seekBar;
    RelativeLayout loading_screen;
    ArrayList<LocationHelperClass> locations=new ArrayList<>();
    int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category_places);
        loading_screen=findViewById(R.id.locations_loading_screen);
        String id=getIntent().getStringExtra("id");
        img=getIntent().getIntExtra("img",0);


        location_recycler=findViewById(R.id.locationsRecycler);
        locationRecycler();


        seekBar=findViewById(R.id.seekBar);
        radius_text=findViewById(R.id.radius_text);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int prog;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog = ((int)Math.round(progress/1000))*1000;
                if (fromUser){
                    radius_text.setText(String.valueOf(prog/1000)+" km");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("check","start tracking");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    loading_screen.setVisibility(View.VISIBLE);
                    UserDashboard.data=null;
                    FetchingData fetchingData=new FetchingData();
                    fetchingData.getLocationData(SingleCategoryPlaces.this,prog,id);
                    locations.clear();
                    adapter.notifyDataSetChanged();

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Log.d("msg", "running");
                            if (UserDashboard.data!=null) {
                                runOnUiThread(new Runnable() { // important to run on a different thread
                                    @Override
                                    public void run() {
                                        Log.d("msg", "not null");
                                        locationRecycler();
                                    }
                                });
                                timer.cancel();
                            }
                        }
                    }, 0, 500);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("check","stop tracking");
            }
        });

    }

    // All locations of a category Recycler view
    private void locationRecycler()  {
        loading_screen.setVisibility(View.GONE);
        location_recycler.setHasFixedSize(true);
        location_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        JSONArray data=UserDashboard.data;
        Log.d("data", String.valueOf(data));
        for (int i=0;i<data.length();i++){
            JSONObject d= null;
            try {
                d = data.getJSONObject(i);

            JSONObject loc=d.getJSONObject("properties");
            Log.d("data1", String.valueOf(loc));

            String name=loc.getString("name");
            String address=loc.getString("address_line2");
            String distance=loc.getString("distance");
            lon=loc.getDouble("lon");
            lat=loc.getDouble("lat");

            Log.d("data1", name+"  "+address+"  "+lat);
            locations.add(new LocationHelperClass(name,address,distance,img,lat,lon));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter =new LocationAdapter(locations,this);
        location_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //when back pressed the main data should be none so that data can be updated whenever this page occurs
    @Override
    public void onBackPressed() {
        UserDashboard.data=null;
        super.onBackPressed();
    }

    //Click on the Get direction button on any location view
    @Override
    public void onGetDirectionClick(int position, ArrayList<LocationHelperClass> location) {
        Intent intent=new Intent(getApplicationContext(), MapScreen.class);
        intent.putExtra("lat",location.get(position).getLat());
        intent.putExtra("lon",location.get(position).getLon());
        intent.putExtra("activity","singleCategoryPlaces");
        startActivity(intent);
    }
}
