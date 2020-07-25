package com.example.tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    FusedLocationProviderClient client;
    SupportMapFragment supportMapFragment;
    Double la,lo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Double lati=getIntent().getExtras().getDouble("latitude");
        Double longi=getIntent().getExtras().getDouble("longitude");
        la=lati;
        lo=longi;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapp);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
map=googleMap;
        LatLng current=new LatLng(la,lo);
        map.addMarker(new MarkerOptions().position(current).title("current"));
        map.moveCamera(CameraUpdateFactory.newLatLng(current));

    }
}