package com.openclassrooms.realestatemanager.management.realestateedit;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.management.addrealestate.RealEstateAddFragment;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateEditFragment extends RealEstateAddFragment {
    private static final String TAG = RealEstateEditFragment.class.getName();

    private List<RealEstateImage> realEstateImageListRetrieved;


    public RealEstateEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRealEstateViewModel.getSelectedRealEstate().observe(this,this::updateWithSelectedRealEstate);


    }

    private void updateWithSelectedRealEstate(RealEstate realEstate){
        mRealEstateViewModel.realEstate.setValue(realEstate);
        updateNearbyCheckButton(realEstate);


        Log.d(TAG, " place near by "+mRealEstateViewModel.nearbyValues);
       // mEstateImages
          ArrayList<RealEstateImage>images = Utils.jsonStringToRealEstateImageList(realEstate.getImages());

                for(RealEstateImage realEstateImage:images){
                    updateImageList(realEstateImage);
                }
        Log.d(TAG, " images size "+mEstateImages.size());
    }


    private void updateNearbyCheckButton(RealEstate realEstate){
        List<String>nearbyList=realEstate.getNearbyPointOfInterest();

        if(nearbyList.contains(getResources().getString(R.string.park)))
            binding.parkCheckBox.setChecked(true);
         if(nearbyList.contains(getResources().getString(R.string.school)))
             binding.schoolCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.hospital)))
            binding.hospitalCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.bus_station)))
            binding.busStationCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.shopping_center)))
            binding.shoppingCenterCheckBox.setChecked(true);
        if(nearbyList.contains(getResources().getString(R.string.sport_center)))
            binding.sportCenterCheckBox.setChecked(true);

    }
}
