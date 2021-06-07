package com.jkrepolido.kalakal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;


public class Profile extends Fragment {
    TextView nameHolder, addressHolder;
    ImageView photoHolder;
    TextView signOut;
    Button btnEKalakal;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public Profile() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        
        photoHolder = view.findViewById(R.id.profilePhoto);
        nameHolder = view.findViewById(R.id.userName);
        addressHolder = view.findViewById(R.id.userAddress);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

        firebaseUser = mAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String name = String.valueOf(dataSnapshot.child("uName").getValue());
                        String address = String.valueOf(dataSnapshot.child("uAddress").getValue());
                        String image = String.valueOf(dataSnapshot.child("uImage").getValue());

                        nameHolder.setText(name);
                        addressHolder.setText(address);
                        Glide.with(photoHolder.getContext()).load(image).circleCrop().into(photoHolder);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        btnEKalakal = view.findViewById(R.id.eKalakal);
        btnEKalakal.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new NewPost()).addToBackStack(null).commit();
            }
        });

        signOut = (TextView) view.findViewById(R.id.user_logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Splash.class);
                startActivity(intent);
            }
        });

        return view;
    }
}