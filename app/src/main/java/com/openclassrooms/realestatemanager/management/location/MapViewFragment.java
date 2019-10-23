package com.openclassrooms.realestatemanager.management.location;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.openclassrooms.realestatemanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment  implements OnMapReadyCallback{
    private static final String TAG = MapViewFragment.class.getName();

    // Constant
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    protected LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private GoogleMap mMap;
    private Marker mMarker;


    public MapViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the FusedLocationClient.
        if(getActivity()!=null)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    //------------------------------------------------------------------------------------------------
    //        PERMISSION AND LOCATION AND UPDATE.
    //------------------------------------------------------------------------------------------------

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getDeviceLocation();
                    // startTrackingLocation();
                } else {
                    Toast.makeText(getContext(),
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * This method to get the device location.
     */
    private void getDeviceLocation() {
        if(getActivity()!=null & getContext()!=null)
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);

            Log.d(TAG, "Location permission do not granted");
        } else {
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), location -> {

                if (location != null) {

                    mCurrentLocation=location;

                    Log.i(TAG, "Location found " + location);

                } else {
                    Log.d(TAG, " Location not found ");
                }
            });
        }
    }

}
