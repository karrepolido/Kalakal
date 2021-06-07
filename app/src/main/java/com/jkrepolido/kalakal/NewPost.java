package com.jkrepolido.kalakal;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class NewPost extends Fragment {
    TextView nameHolder, addressHolder, pNameHolder, cancelPost;
    EditText pCategoryHolder, pValueHolder, pConditionHolder, pDescHolder, pTagHolder, pAvailabilityHolder;
    ImageView photoHolder, pImageHolder;
    Button btnNewPost, btnAddPhoto;
    int Image_Request_Code = 5;
    Uri FilePathUri;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public NewPost() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);
        photoHolder = view.findViewById(R.id.profilePhoto);
        nameHolder = view.findViewById(R.id.userName);
        addressHolder = view.findViewById(R.id.userAddress);

        pImageHolder = view.findViewById(R.id.pImage);
        pNameHolder = view.findViewById(R.id.pName);
        pCategoryHolder = view.findViewById(R.id.pCategory);
        pValueHolder = view.findViewById(R.id.pValue);
        pConditionHolder = view.findViewById(R.id.pCondition);
        pDescHolder = view.findViewById(R.id.pDesc);
        pTagHolder = view.findViewById(R.id.pTags);
        pAvailabilityHolder = view.findViewById(R.id.pAvailability);

        cancelPost = view.findViewById(R.id.cancelPost);
        cancelPost.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new Profile()).addToBackStack(null).commit();
            }
        });

        btnAddPhoto = view.findViewById(R.id.addProdPhoto);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });


        btnNewPost = view.findViewById(R.id.newPost);


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

        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
                pImageHolder.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
}