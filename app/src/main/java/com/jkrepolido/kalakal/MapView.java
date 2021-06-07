package com.jkrepolido.kalakal;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static androidx.core.content.ContextCompat.getSystemService;


public class MapView extends Fragment {
    LocationManager locationManager;
    GoogleMap mMap;
    private final int REQUEST_CODE = 70;
    private double userLat, userLong;
    Location location;
    LatLng userLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                checkLocationPermission();
                mMap.setMyLocationEnabled(true);

                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                String locationProvider = LocationManager.GPS_PROVIDER;

                location = locationManager.getLastKnownLocation(locationProvider);

                if (location != null) {
                    currentLocation(location);
                }

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.clear();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        mMap.addMarker(new MarkerOptions().position(latLng));
                        mMap.addCircle(new CircleOptions()
                                .center(latLng)
                                .radius(5000)
                                .strokeWidth(2)
                                .strokeColor(Color.RED)
                                .fillColor(Color.parseColor("#33DF5E88")));

                    }
                });

            }
        });

        return view;
    }

    public boolean checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission( getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale( getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION ) )
            {
                //Prompt the user once explanation has been shown
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE
                );
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE );
            }
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkLocationPermission();
                }
                break;
        }
    }

    public void currentLocation(Location location) {
        userLat = location.getLatitude();
        userLong = location.getLongitude();
        userLocation = new LatLng (userLat, userLong);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12));
        mMap.addMarker(new MarkerOptions().position(userLocation));
        mMap.addCircle(new CircleOptions()
                .center(userLocation)
                .radius(5000)
                .strokeWidth(2)
                .strokeColor(Color.RED)
                .fillColor(Color.parseColor("#33DF5E88")));

    }


}