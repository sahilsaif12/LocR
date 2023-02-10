package com.example.locr.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.locr.Common.MapScreen;
import com.example.locr.HelperClasses.DirectionViewInterface;
import com.example.locr.HelperClasses.HomeAdapter.LocationAdapter;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.LocationHelperClass;
import com.example.locr.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleCategoryPlaces extends AppCompatActivity implements DirectionViewInterface {

    RecyclerView location_recycler;
    RecyclerView.Adapter adapter;
    Double lat,lon;
    int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category_places);
//        String id=getIntent().getStringExtra("id");
        img=getIntent().getIntExtra("img",0);
//        category_id.setText(id);

        location_recycler=findViewById(R.id.locationsRecycler);
            locationRecycler();

    }

    private void locationRecycler()  {
        location_recycler.setHasFixedSize(true);
        location_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ArrayList<LocationHelperClass> locations=new ArrayList<>();
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
//        locations.add(new LocationHelperClass("Djlkfjl fkjdsifjjiejij wij9jijidj dfjijdflkj lkj klj kjijoij  jlkjk","slkfjdkljf ejieoijej dfjdlfhdj eqiijowij ew dsdhuhoqij dsijdifjsdijsd dijsdj","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
//        locations.add(new LocationHelperClass("Djlkfjl","LdSJLKJLFJFLHOUEHUOE LEJJD OIEHOUEH","2803",img,UserDashboard.lat,UserDashboard.lon));
        adapter =new LocationAdapter(locations,this);
        location_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        UserDashboard.data=null;
        super.onBackPressed();
    }


    @Override
    public void onGetDirectionClick(int position, ArrayList<LocationHelperClass> location) {
        Intent intent=new Intent(getApplicationContext(), MapScreen.class);
        intent.putExtra("lat",location.get(position).getLat());
        intent.putExtra("lon",location.get(position).getLon());
        intent.putExtra("getDirection","getDirection");
        startActivity(intent);
    }
}
