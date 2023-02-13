package com.example.locr.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locr.R;
import com.example.locr.User.UserDashboard;

public class MainActivity extends AppCompatActivity {
    private int SPLASH_TIME=5000;
    ImageView splashImg;
    TextView appName,poweredBy;
    Animation sideAnim,btmAnim;
    SharedPreferences onBoardingScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splashImg=findViewById(R.id.splash_img);
        appName=findViewById(R.id.appName);
        poweredBy=findViewById(R.id.poweredBy);

        sideAnim= AnimationUtils.loadAnimation(this,R.anim.side_animation);
        btmAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        splashImg.setAnimation(sideAnim);
        appName.setAnimation(btmAnim);
        poweredBy.setAnimation(btmAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoardingScreen=getSharedPreferences("onBoarding",MODE_PRIVATE);
                boolean isFirstTime=onBoardingScreen.getBoolean("isFirstTime",true);

                // Checking the user open the app first time or not so that it can decide to show OnBoarding screen or not
                if (isFirstTime){
                    SharedPreferences.Editor editor=onBoardingScreen.edit();
                    editor.putBoolean("isFirstTime",false);
                    editor.commit();
                    Intent intent=new Intent(getApplicationContext(), OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent=new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intent);
                    finish();
                }

            }
        },SPLASH_TIME);
    }
}