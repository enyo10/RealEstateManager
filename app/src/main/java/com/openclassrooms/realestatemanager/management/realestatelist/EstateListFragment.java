package com.openclassrooms.realestatemanager.management.realestatelist;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateListBinding;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateListFragment extends Fragment{
    private static final String TAG =EstateListFragment.class.getName();
    private static int USER_ID=1;

    protected FragmentEstateListBinding mBinding;
    public  RealEstateRecyclerViewAdapter mRealEstateRecyclerViewAdapter;
    protected RealEstateViewModel mRealEstateViewModel;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_estate_list,container,false);


         return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "in on activity created");

        if(getActivity()!=null){

            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;

        }

        initAndConfigureRecyclerView();

        getRealEstateItems(USER_ID);
    }

    public void initAndConfigureRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.mBinding.realEstateListRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRealEstateRecyclerViewAdapter = new RealEstateRecyclerViewAdapter(getActivity(),mRealEstateViewModel);
        this.mBinding.realEstateListRecyclerView.setAdapter(mRealEstateRecyclerViewAdapter);

    }


    protected void updateRealEstateList(List<RealEstate>list){
       mRealEstateRecyclerViewAdapter.updateWithData(list);
    }

    // Get all RealEstate for a given  user id.
    protected void getRealEstateItems(long userId) {
        this.mRealEstateViewModel.getRealEstates(userId).observe(this, this::updateRealEstateList);
    }



}
