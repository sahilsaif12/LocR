package com.example.locr.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.FeaturedHelperClass;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.MostViewedHelperClass;
import com.example.locr.R;

import java.util.ArrayList;

public class MostViewedAdapter extends RecyclerView.Adapter<MostViewedAdapter.MostViewedViewHolder> {
    ArrayList<MostViewedHelperClass> mostViewedLocation;


    public MostViewedAdapter(ArrayList<MostViewedHelperClass> mostViewedLocation) {
        this.mostViewedLocation = mostViewedLocation;

    }


    @NonNull
    @Override
    public MostViewedAdapter.MostViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.most_viewed_card_layout,parent,false);
        MostViewedViewHolder mostViewedViewHolder=new MostViewedViewHolder(view);

        return mostViewedViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MostViewedAdapter.MostViewedViewHolder holder, int position) {
        MostViewedHelperClass mostViewedHelperClass =mostViewedLocation.get(position);

        holder.image.setImageResource(mostViewedHelperClass.getImage());
        holder.title.setText(mostViewedHelperClass.getTitle());
        holder.desc.setText(mostViewedHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return mostViewedLocation.size();
    }

    public static class MostViewedViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        ImageView image;
        public MostViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.most_viewed_title);
            desc=itemView.findViewById(R.id.most_viewed_desc);
            image=itemView.findViewById(R.id.most_viewed_img);
        }

    }
}
