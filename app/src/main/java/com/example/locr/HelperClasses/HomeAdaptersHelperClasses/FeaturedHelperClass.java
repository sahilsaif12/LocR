package com.example.locr.HelperClasses.HomeAdaptersHelperClasses;

public class FeaturedHelperClass {
    String image;
    String title ,desc;

    public FeaturedHelperClass(String image, String title, String desc) {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }


    public String getImage() {
        return image;
    }


    public String getTitle() {
        return title;
    }


    public String getDesc() {
        return desc;
    }

}
