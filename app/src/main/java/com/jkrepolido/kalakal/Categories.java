package com.jkrepolido.kalakal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jkrepolido.kalakal.Adapter.CategoriesAdapter;
import com.jkrepolido.kalakal.Adapter.ProductsAdapter;
import com.jkrepolido.kalakal.ModelClass.CategoriesModel;
import com.jkrepolido.kalakal.ModelClass.ProductsModel;

public class Categories extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    CategoriesAdapter categoriesAdapter;

    public Categories() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        ImageView imageView1 = view.findViewById(R.id.clothing);
        ImageView imageView2 = view.findViewById(R.id.carpentry);
        ImageView imageView3 = view.findViewById(R.id.computer);
        ImageView imageView4 = view.findViewById(R.id.automobile);
        ImageView imageView5 = view.findViewById(R.id.food);
        ImageView imageView6 = view.findViewById(R.id.stationery);
        ImageView imageView7 = view.findViewById(R.id.sports);
        ImageView imageView8 = view.findViewById(R.id.painting);
        ImageView imageView9 = view.findViewById(R.id.jewelry);
        ImageView imageView10 = view.findViewById(R.id.kitchen);
        ImageView imageView11 = view.findViewById(R.id.games);
        ImageView imageView12 = view.findViewById(R.id.mobile);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        imageView11.setOnClickListener(this);
        imageView12.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment= new Maintenance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}