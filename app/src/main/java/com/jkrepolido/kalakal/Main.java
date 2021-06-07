package com.jkrepolido.kalakal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Home()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.ic_home:
                    selectedFragment = new Home();
                    break;
                case R.id.ic_location:
                    selectedFragment = new MapView();
                    break;
                case R.id.ic_category:
                    selectedFragment = new Categories();
                    break;
                case R.id.ic_chat:
                    //selectedFragment = new Chatroom();
                    //for the meantime use this for presentation purposes
                    selectedFragment = new Maintenance();
                    break;
                case R.id.ic_profile:
                    selectedFragment = new Profile();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag == null) {
                super.onBackPressed();
                finish();
                return;
            }
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getFragments() == null) {
                    super.onBackPressed();
                    finish();
                    return;
                }
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
                else {

                    fm.popBackStack();
                    if (fm.getFragments().size() <= 1) {
                        finish();
                    }
                    return;
                }

            }
        }
    }
}