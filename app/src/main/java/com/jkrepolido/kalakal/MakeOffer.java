package com.jkrepolido.kalakal;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jkrepolido.kalakal.ModelClass.BarterOffers;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class MakeOffer extends Fragment {

    EditText bItem, bValue, bCondition, bDetails;
    ImageView bImage;
    Button addPhoto, sendOffer, cancelOffer;
    Uri FilePathUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    BarterOffers barterOffers;

    public MakeOffer() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_offer, container, false);

        bItem = (EditText) view.findViewById(R.id.barterItem);
        bValue = (EditText) view.findViewById(R.id.barterValue);
        bCondition = (EditText) view.findViewById(R.id.barterCondition);
        bDetails = (EditText) view.findViewById(R.id.barterDetails);
        bImage = (ImageView) view.findViewById(R.id.barterImage);
        addPhoto = (Button) view.findViewById(R.id.addPhoto);
        sendOffer = (Button) view.findViewById(R.id.sendOffer);
        progressDialog = new ProgressDialog(getActivity());

        barterOffers = new BarterOffers();
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BarterOffers");

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                barterOffers.setbItem(bItem.getText().toString().trim());
                barterOffers.setbValue(bValue.getText().toString().trim());
                barterOffers.setbCondition(bCondition.getText().toString().trim());
                barterOffers.setbDetails(bDetails.getText().toString().trim());
                barterOffers.setbImageURL(databaseReference.push().getKey());

                databaseReference.push().setValue(barterOffers);
                uploadToDatabase();
                //Toast.makeText(getActivity(), "Your offer was successfully sent.", Toast.LENGTH_LONG).show();
            }
        });

        cancelOffer = view.findViewById(R.id.cancelOffer);
        cancelOffer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new Home()).addToBackStack(null).commit();
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
                bImage.setImageBitmap(bitmap);
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

    public void uploadToDatabase() {


        if (FilePathUri != null) {

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Sending your offer...");
            progressDialog.show();
            //UUID.randomUUID().toString()
            //System.currentTimeMillis() + "." + GetFileExtension(FilePathUri)
            StorageReference ref= storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            ref.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String item = bItem.getText().toString().trim();
                            String value = bValue.getText().toString().trim();
                            String condition = bCondition.getText().toString().trim();
                            String details = bDetails.getText().toString().trim();

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Your offer was successfully sent", Toast.LENGTH_LONG).show();


                            //barterOffers = new BarterOffers(item, value, condition, details, taskSnapshot.getUploadSessionUri().toString());
                            String bImage = databaseReference.push().getKey();
                            //databaseReference.child(bImage).setValue(bImage);

                            Intent intent = new Intent(getContext(), Main.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductContent()).addToBackStack(null).commit();
    }
}