package com.openclassrooms.realestatemanager.management.location;


import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseFragment;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends BaseFragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{
    private static final String TAG = MapViewFragment.class.getName();

    // Constant
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    public static final int USER_ID = 1;


    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private Location mCurrentLocation;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
//    PlacesClient mPlacesClient;
    private GoogleMap mMap;
    private Marker mMarker;
    private RealEstateViewModel mRealEstateViewModel;


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
        // Location request.
        createLocationRequest();
        getCurrentLocationSettings();
        this.initMap();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "in on activity created");

        if(getActivity()!=null){
            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
        }


        getAllRealEstateAndShowTheirLocation(USER_ID);
    }

    //---------------------
    // Map
    //---------------------

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.fragment_map);
        if(mapFragment!=null)
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       // getLocationPermission();

       /* LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/

        //updateUI();
        updateLocationUI();
        mMap.setOnMarkerClickListener(this);
       // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getActivity()));

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
                   //  startTrackingLocation();
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
                    mLastKnownLocation=location;

                    Log.i(TAG, "Location found  " + location);
                    updateLocationUI();

                } else {
                    Log.d(TAG, " Location not found ");
                }
            });
        }
    }



    private void createLocationRequest() {
        mLocationRequest  = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


   private void  getCurrentLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    //---------------------
    // Ui
    //---------------------
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLastKnownLocation != null) {
                Log.d(TAG, " last know location not null");
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
               // mMap.getUiSettings().setCompassEnabled(true);

               /* mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                mMap.getUiSettings().setZoomControlsEnabled(true);

*/
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /*private void updateUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                getDeviceLocation();
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                mLastKnownLocation = null;
                //Try to obtain location permission
                //getLocationPermission();

            }
        } catch (SecurityException e) {
            Log.e(TAG, "updateUI: SecurityException " + e.getMessage());
        }
    }*/

   /* @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }*/





    private void startLocationUpdates() {
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        try {
            if (mMarker.isInfoWindowShown()) {
                mMarker.hideInfoWindow();
            } else {
                mMarker.showInfoWindow();
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "onClick: NullPointerException: " + e.getMessage());
        }
        return false;
    }

    private void geoLocateRealEstate(String realEstateAddress, String snippet){

        Log.d(TAG, "geoLocate: geolocating");

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(realEstateAddress, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());

            mMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(address.getLatitude(), address.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title(address.getAddressLine(0))
                    .snippet(snippet));
        }

    }

    private void markRealEstatesAddresses(List<RealEstate> realEstateList) {

        for (int i = 0; i < realEstateList.size(); i++) {
            /*String address = realEstateList.get(i).getAddress().line1 + " " + realEstateList.get(i).getAddress().line2
                    + realEstateList.get(i).getAddress().city + " " + realEstateList.get(i).getAddress().state + " "
                    + realEstateList.get(i).getAddress().zip;*/
            String address=realEstateList.get(i).getAddress().format();
            String snippet=realEstateList.get(i).formatSnippet();

            /*String snippet = Utils.formatSnippet(realEstateList.get(i).getType(), realEstateList.get(i).getArea(),
                    realEstateList.get(i).getPrice(),realEstateList.get(i).getSurface(), realEstateList.get(i).getRoom(),
                    realEstateList.get(i).getBathroom(), realEstateList.get(i).getBedroom());*/
            geoLocateRealEstate(address, snippet);

        }
    }

    // Get all items for a user
    private void getAllRealEstateAndShowTheirLocation(int userId) {
        this.mRealEstateViewModel.getRealEstates(userId).observe(this,this::markRealEstatesAddresses);

    }
}
