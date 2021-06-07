package com.jkrepolido.kalakal;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jkrepolido.kalakal.Adapter.ProductsAdapter;
import com.jkrepolido.kalakal.ModelClass.ProductsModel;

public class ProductContent extends Fragment {

    ImageView pImageHolder, pImageTrader;
    TextView pNameHolder, pDescHolder, pTraderHolder, pDistanceHolder;
    String pImage, pName, pDesc, pTrader, pTraderPhoto, pDistance;
    Button btnOffer, btnChat;

    public ProductContent() {}

    public ProductContent(String pImage, String pName, String pDesc, String pTrader, String pTraderPhoto, String pDistance) {
        this.pImage = pImage;
        this.pName = pName;
        this.pDesc = pDesc;
        this.pTrader = pTrader;
        this.pTraderPhoto = pTraderPhoto;
        this.pDistance = pDistance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_content, container, false);

        pImageHolder = view.findViewById(R.id.pImage);
        pNameHolder = view.findViewById(R.id.pName);
        pDescHolder = view.findViewById(R.id.pDesc);
        pTraderHolder = view.findViewById(R.id.pTrader);
        pImageTrader = view.findViewById(R.id.pTraderPhoto);
        pDistanceHolder = view.findViewById(R.id.pDistance);

        Glide.with(getContext()).load(pImage).into(pImageHolder);
        pNameHolder.setText(pName);
        pDescHolder.setText(pDesc);
        pTraderHolder.setText(pTrader);
        pDistanceHolder.setText(pDistance);
        Glide.with(getContext()).load(pTraderPhoto).circleCrop().into(pImageTrader);

        btnOffer = view.findViewById(R.id.makeOffer);
        btnOffer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new MakeOffer()).addToBackStack(null).commit();
            }
        });

        btnChat = view.findViewById(R.id.chatUser);
        btnChat.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.frameLayout, new Chatroom()).addToBackStack(null).commit();
                //For the meantime, use this for presentation purposes
                fragmentTransaction.replace(R.id.frameLayout, new Maintenance()).addToBackStack(null).commit();
            }
        });

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).addToBackStack(null).commit();
    }

}