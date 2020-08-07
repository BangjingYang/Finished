package com.example.a3.nv_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.a3.Login;
import com.example.a3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class Map_Fragment extends Fragment implements OnMapReadyCallback{

    View vmap;
    private MapView mMap;
    private GoogleMap gMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vmap = inflater.inflate(R.layout.fragment_map, container, false);
        mMap = vmap.findViewById(R.id.mapview);
        if (mMap != null) {
            mMap.onCreate(null);
            mMap.onResume();
            mMap.getMapAsync(this);
        }
        return vmap;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
//        mMap = vmap.findViewById(R.id.mapview);
//        if (mMap != null) {
//            mMap.onCreate(null);
//            mMap.onResume();
//            mMap.getMapAsync(this);
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng location = new LatLng(-37.8133262,144.9413978);//(-37.8133262, 144.9413978)
        gMap.addMarker(new MarkerOptions().position(location).title("Home"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.0f));
    }
}
