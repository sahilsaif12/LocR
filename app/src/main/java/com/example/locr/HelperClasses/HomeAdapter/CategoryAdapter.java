package com.example.locr.HelperClasses.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locr.HelperClasses.CategoryViewInterface;
import com.example.locr.HelperClasses.HomeAdaptersHelperClasses.CategoriesHelperClass;
import com.example.locr.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final CategoryViewInterface categoryViewInterface;
    ArrayList<CategoriesHelperClass> category;


    public CategoryAdapter(ArrayList<CategoriesHelperClass> category, CategoryViewInterface categoryViewInterface) {
        this.category = category;
        this.categoryViewInterface=categoryViewInterface;

    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout,parent,false);
        CategoryViewHolder categoryViewHolder=new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoriesHelperClass categoriesHelperClass=category.get(position);
        holder.image.setImageResource(categoriesHelperClass.getImage());
        holder.title.setText(categoriesHelperClass.getTitle());
        holder.card.setBackground(categoriesHelperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        CardView card;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.category_title);
            image=itemView.findViewById(R.id.location_img);
            card=itemView.findViewById(R.id.catagory_box);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (categoryViewInterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            categoryViewInterface.onItemClick(pos,category);
                        }
                    }
                }
            });
        }


    }
}
