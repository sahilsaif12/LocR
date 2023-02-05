package com.example.locr.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.FeaturedHelperClass;
import com.example.locr.R;

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

        holder.image.setImageResource(featuredHelperClass.getImage());
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
