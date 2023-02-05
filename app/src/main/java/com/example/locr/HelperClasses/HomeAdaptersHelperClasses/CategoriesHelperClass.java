package com.example.locr.HelperClasses.HomeAdaptersHelperClasses;

import android.graphics.drawable.GradientDrawable;

public class CategoriesHelperClass {
    int image;
    GradientDrawable gradient;
    String title ;

    public CategoriesHelperClass(GradientDrawable gradient,int image, String title) {
        this.image = image;
        this.title = title;
        this.gradient=gradient;
    }

    public int getImage() {
        return image;
    }

    public GradientDrawable getGradient() {
        return gradient;
    }


    public String getTitle() {
        return title;
    }


}
