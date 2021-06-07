package com.jkrepolido.kalakal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.jkrepolido.kalakal.ModelClass.BarterOffers;
import com.jkrepolido.kalakal.ModelClass.UsersModel;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText fullname, uAddress, emailAddress, rPassword;
    Button registerBtn;
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    UsersModel users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        fullname = (EditText) findViewById(R.id.fullname);
        uAddress = (EditText) findViewById(R.id.address);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        rPassword = (EditText) findViewById(R.id.rPassword);
        registerBtn = (Button)findViewById(R.id.registerBtn);

        users = new UsersModel();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameHolder = fullname.getText().toString().trim();
                String addressHolder = uAddress.getText().toString().trim();
                String emailHolder = emailAddress.getText().toString().trim();
                String passwordHolder = rPassword.getText().toString().trim();


                if (TextUtils.isEmpty(nameHolder)) {
                    fullname.setError("Full name is required.");
                    return;
                }

                if (TextUtils.isEmpty(addressHolder)) {
                    uAddress.setError("Address is required.");
                    return;
                }

                if (TextUtils.isEmpty(emailHolder)) {
                    emailAddress.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(passwordHolder)) {
                    rPassword.setError("Password is required.");
                    return;
                }

                if (passwordHolder.length() < 6) {
                    rPassword.setError("Password must be more than 6 characters.");
                    return;
                }

                users.setuName(nameHolder);
                users.setuAddress(addressHolder);
                users.setuEmail(emailHolder);
                users.setuPassword(passwordHolder);
                databaseReference.push().setValue(users);

                //using firebase
                mAuth.createUserWithEmailAndPassword(emailHolder, passwordHolder).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            //String userid = firebaseUser.getUid();
                            String nameHolder = fullname.getText().toString().trim();

                            mAuthListener = new FirebaseAuth.AuthStateListener() {
                                @Override
                                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(nameHolder).build();
                                        user.updateProfile(profileUpdates);
                                        Intent intent = new Intent(getApplicationContext(), Main.class);
                                        startActivity(intent);
                                    }
                                }
                            };
                        } else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, Main.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }

}