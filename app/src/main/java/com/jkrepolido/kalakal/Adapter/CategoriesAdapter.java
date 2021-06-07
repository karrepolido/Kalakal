package com.jkrepolido.kalakal.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.jkrepolido.kalakal.ModelClass.CategoriesModel;
import com.jkrepolido.kalakal.R;

public class CategoriesAdapter extends FirebaseRecyclerAdapter<CategoriesModel, CategoriesAdapter.MyViewHolder> {

    public CategoriesAdapter (@NonNull FirebaseRecyclerOptions<CategoriesModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list, parent, false);
        return new CategoriesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CategoriesModel categoriesModel) {
        Glide.with(holder.catimage.getContext()).load(categoriesModel.getcatImage()).into(holder.catimage);
        holder.catname.setText(categoriesModel.getcatName());


        /**holder.catimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductContent(categoriesModel.getCatImage(), categoriesModel.getCatName())).addToBackStack(null).commit();
            }
        }); */

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView catimage;
        TextView catname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catimage = itemView.findViewById(R.id.catImage);
            catname = itemView.findViewById(R.id.catName);
        }

    }
}
