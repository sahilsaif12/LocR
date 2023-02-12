package com.example.locr.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.HelperClasses.DirectionViewInterface;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.LocationHelperClass;
import com.example.locr.R;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    private final DirectionViewInterface directionViewInterface;

    ArrayList<LocationHelperClass> locations;

    public LocationAdapter(ArrayList<LocationHelperClass> locations,DirectionViewInterface directionViewInterface) {
        this.locations = locations;
        this.directionViewInterface=directionViewInterface;
    }


    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card_layout,parent,false);
        LocationViewHolder locationViewHolder=new LocationViewHolder(view);
        return locationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        LocationHelperClass locationHelperClass=locations.get(position);
        holder.name.setText(locationHelperClass.getName());
        holder.address.setText(locationHelperClass.getAddress());
        String dis=String.format("%.2f",Float.valueOf(locationHelperClass.getDistance())/1000);
        holder.distance.setText(dis+" km");
        holder.image.setImageResource(locationHelperClass.getImage());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,distance;
        Button direction;
        ImageView image;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.location_name);
            address=itemView.findViewById(R.id.location_address);
            distance=itemView.findViewById(R.id.location_distance);
            image=itemView.findViewById(R.id.location_img);
            direction=itemView.findViewById(R.id.get_location_direction);

            direction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (directionViewInterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            directionViewInterface.onGetDirectionClick(pos,locations);
                        }
                    }
                }
            });
        }
    }
}

