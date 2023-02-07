package com.example.locr.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.locr.HelperClasses.SliderAdapter;
import com.example.locr.R;
import com.example.locr.User.UserDashboard;

public class OnBoarding extends AppCompatActivity {
    ViewPager slider;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Button letsGetStarted;
    Animation animation;
    int current_pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        slider=findViewById(R.id.slider);
        dotsLayout=findViewById(R.id.dots);
        letsGetStarted=findViewById(R.id.letsGetStarted);

        sliderAdapter=new SliderAdapter(this);
        slider.setAdapter(sliderAdapter);

        addDots(0);
        slider.addOnPageChangeListener(changeListener);
    }

    private void addDots(int pos){
        dots=new TextView[4];
        dotsLayout.removeAllViews();
        for (int i = 0; i<dots.length; i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length>0){
            dots[pos].setTextColor(getResources().getColor(R.color.color_primary));
        }
    }

    ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            current_pos=position;
            addDots(position);
            if (position==0){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }
            else if (position==1){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }
            else if (position==2){
                letsGetStarted.setVisibility(View.INVISIBLE);
            }
            else{
                letsGetStarted.setVisibility(View.VISIBLE);
                animation= AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom_animation);
                animation.setDuration(500);
                letsGetStarted.setAnimation(animation);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void skip(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }

    public void getStarted(View view) {
        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
    }
    public void next(View view) {
        slider.setCurrentItem(current_pos+1);
    }
}