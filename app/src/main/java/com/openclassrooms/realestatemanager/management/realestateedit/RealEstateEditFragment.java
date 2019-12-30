package com.openclassrooms.realestatemanager.management.realestateedit;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.management.addrealestate.AddImageRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.management.addrealestate.RealEstateAddFragment;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateEditFragment extends RealEstateAddFragment  {
    private static final String TAG = RealEstateEditFragment.class.getName();

    private RealEstate mRealEstate;
    private AddImageRecyclerViewAdapter mAddImageRecyclerViewAdapter;


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

        binding.realEstateAddSaveButton.setText(getResources().getString(R.string.update));
        binding.realEstateUpdateStatus.setVisibility(View.VISIBLE);
        binding.realEstateUpdateStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(mRealEstateViewModel.realEstate!=null)
                    if(checkedId==R.id.sold_radio_button){
                        mRealEstateViewModel.realEstate.getValue().setSold(true);
                        mRealEstateViewModel.realEstate.getValue().setDateOfSale(new Date());
                    }
                    else if(checkedId==R.id.unsold_radio_button){
                        mRealEstateViewModel.realEstate.getValue().setSold(false);
                        mRealEstateViewModel.realEstate.getValue().setDateOfSale(null);
                    }

            }
        });
        mRealEstateViewModel.getSelectedRealEstate().observe(this,this::updateWithSelectedRealEstate);


    }

    private void updateWithSelectedRealEstate(RealEstate realEstate){
        mRealEstateViewModel.realEstate.setValue(realEstate);

        updateNearbyCheckButton(realEstate);
        loadImageListAndUpdateUI(realEstate);



    }

    /**
     * This method to retrieve the list of real estate pictures.
     * @param realEstate, the real estate to update.
     */
    private void loadImageListAndUpdateUI(RealEstate realEstate){

        ArrayList<RealEstateImage>images = Utils.jsonStringToRealEstateImageList(realEstate.getImages());

        for(RealEstateImage realEstateImage:images) {
            if(realEstateImage.getBitmap()!=null)
                Log.d(TAG, " Bitmap "+realEstateImage.getBitmap());
            Log.d(TAG, " image  " + realEstateImage);
            if (mEstateImages != null)
                Log.d(TAG, "images size " + mEstateImages.size());
            if (mAddImageRecyclerViewAdapter != null) {
                Log.d(TAG, "adapter not null");
                 updateImageList(realEstateImage);
            }
        }
    }


    /**
     * this method update the state of the check button,
     * @param realEstate, the real estate to be edited.
     */
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

        if(realEstate.isSold())
            binding.soldRadioButton.setChecked(true);
        else binding.soldRadioButton.setChecked(false);

    }



}
