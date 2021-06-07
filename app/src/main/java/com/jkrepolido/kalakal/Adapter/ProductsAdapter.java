package com.jkrepolido.kalakal.Adapter;

import android.content.Context;
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
import com.jkrepolido.kalakal.ModelClass.ProductsModel;
import com.jkrepolido.kalakal.ProductContent;
import com.jkrepolido.kalakal.R;


public class ProductsAdapter extends FirebaseRecyclerAdapter<ProductsModel, ProductsAdapter.MyViewHolder> {
    ProductsModel productsModel;

    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<ProductsModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ProductsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull ProductsModel productsModel) {
        Glide.with(holder.pimage.getContext()).load(productsModel.getpImage()).into(holder.pimage);
        holder.pname.setText(productsModel.getpName());
        holder.pdesc.setText(productsModel.getpDesc());


            holder.pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductContent(productsModel.getpImage(), productsModel.getpName(),productsModel.getpDesc(), productsModel.getpTrader(), productsModel.getpTraderPhoto(), productsModel.getpDistance())).addToBackStack(null).commit();
            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView pimage;
        TextView pname, pdesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pimage = itemView.findViewById(R.id.pImage);
            pname = itemView.findViewById(R.id.pName);
            pdesc = itemView.findViewById(R.id.pDesc);
        }

    }

}
