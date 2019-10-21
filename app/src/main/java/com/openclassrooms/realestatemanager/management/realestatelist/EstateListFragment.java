package com.openclassrooms.realestatemanager.management.realestatelist;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.openclassrooms.realestatemanager.management.realestatedetails.EstateDetailsFragment;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

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
    private EstateDetailsFragment mEstateDetailsFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_estate_list,container,false);
         boolean value= getContext().getResources().getBoolean(R.bool.isTablet);
         if(value)
          //   configureAndShowDetailFragment();

         Log.i(TAG, " is tablet.. "+value);


        return mBinding.getRoot();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

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

    protected void initAndConfigureRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.mBinding.realEstateListRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRealEstateRecyclerViewAdapter = new RealEstateRecyclerViewAdapter(getActivity(),mRealEstateViewModel);
        this.mBinding.realEstateListRecyclerView.setAdapter(mRealEstateRecyclerViewAdapter);

    }


    protected void updateRealEstateList(List<RealEstate>list){
       mRealEstateRecyclerViewAdapter.updateWithData(list);
       for(RealEstate estate:list)
           Log.d(TAG, " status -- "+estate.isSold());
    }

    // Get all RealEstate for a given  user id.
    protected void getRealEstateItems(long userId) {
        this.mRealEstateViewModel.getRealEstates(userId).observe(this, this::updateRealEstateList);
    }




    private void configureAndShowDetailFragment(){
        Log.d(TAG, " Configure and show Fragment ");
        mEstateDetailsFragment = (EstateDetailsFragment) getChildFragmentManager().findFragmentById(R.id.frame_estate_details_Fragment);

        Log.d(TAG, " details fragment " +mEstateDetailsFragment);

        //A - We only add DetailFragment in Tablet mode (If found frame_layout_detail)
        if (mEstateDetailsFragment == null) {
            Log.i(TAG, " nous sommes bien sur une tablette ");
            mEstateDetailsFragment = new EstateDetailsFragment();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.frame_estate_details_Fragment, mEstateDetailsFragment)
                    .commit();
            Log.i(TAG, "Fragment created");

        }
    }




}
