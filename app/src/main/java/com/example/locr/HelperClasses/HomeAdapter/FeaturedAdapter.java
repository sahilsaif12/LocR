package com.example.locr.HelperClasses.HomeAdapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.FeaturedHelperClass;
import com.example.locr.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {
    ArrayList<FeaturedHelperClass> featuredLocation;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocation) {
        this.featuredLocation = featuredLocation;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feature_card_layout,parent,false);
        FeaturedViewHolder featuredViewHolder=new FeaturedViewHolder(view);
        return featuredViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {

        FeaturedHelperClass featuredHelperClass =featuredLocation.get(position);

//        try {
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(featuredHelperClass.getImage()).getContent());
//            holder.image.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Picasso
                .get()
                .load(featuredHelperClass.getImage())
                .into(holder.image);
//        holder.image.setImageResource(featuredHelperClass.getImage());
        holder.title.setText(featuredHelperClass.getTitle());
        holder.desc.setText(featuredHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return
                featuredLocation.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder{

        TextView title,desc;
        ImageView image;
        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.featured_title);
            desc=itemView.findViewById(R.id.featured_desc);
            image=itemView.findViewById(R.id.featured_img);
        }

    }


}
