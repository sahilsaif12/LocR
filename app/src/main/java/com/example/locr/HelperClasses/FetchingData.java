package com.example.locr.HelperClasses;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.locr.User.UserDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchingData {

    public void getLocationData(Context context , int radius, String category_id) throws IOException {
        Double lat=UserDashboard.lat;
        Double lon=UserDashboard.lon;
        String data ;
        new Thread(new Runnable(){
            @Override
            public void run() {

                ApplicationInfo ai;
                Bundle bundle=null ;
                try {
                    ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    bundle = ai.metaData;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

//                 Do network action in this function
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();

                Request request = null;
                try {
                    request = new Request.Builder()
                            .url(new URL("https://api.geoapify.com/v2/places?categories="+ category_id +"&filter=circle:" +lon+","+lat+"," +radius+"&bias=proximity:"+lon+","+lat +"&limit=50&apiKey=" +bundle.get("geoapify_key")))
                            .method("GET", null)
                            .build();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    String s =response.body().string();
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray data =jsonObject.getJSONArray("features");
                    UserDashboard.data=data;

//                    for (int i=0;i<data.length();i++){
//                        JSONObject d=data.getJSONObject(i);
//                        Log.d("data1", String.valueOf(d));
//                    }
//                    Log.d("data1", s);
//                    Log.d("data1", String.valueOf(data));

                    Log.d("data1", String.valueOf("https://api.geoapify.com/v2/places?categories="+ category_id +"&filter=circle:" +lon+","+lat+"," +radius+"&bias=proximity:"+lon+","+lat +"&limit=50&apiKey=" +bundle.get("geoapify_key")));


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                //                    JSONObject jsonObject=new JSONObject(s);
//                    data[0] =jsonObject.getJSONArray("features");

//                    Log.d("response", );
            }
        }).start();



    }

}
