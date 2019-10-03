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
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateListFragment extends Fragment{
    private static final String TAG =EstateListFragment.class.getName();
    private static long USER_ID=1;

    private FragmentEstateListBinding mBinding;
    private RealEstateRecyclerviewAdapter mRealEstateRecyclerViewAdapter;
    private List<RealEstate> mRealEstateList=new ArrayList<>();
    private RealEstateViewModel mRealEstateViewModel;

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

            RealEstateMainActivity mainActivity=((RealEstateMainActivity) this.getActivity());


            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
            User user =mRealEstateViewModel.getUser(USER_ID).getValue();
            Log.i(TAG," user"+user);


            mRealEstateList= mainActivity.mRealEstateViewModel.getRealEstates(USER_ID).getValue();

            if(mRealEstateList!=null)
            Log.i(TAG," Real estate size "+mRealEstateList.size());
        }

        initAndConfigureRecyclerView();


    }

    private void initAndConfigureRecyclerView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.mBinding.realEstateListRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRealEstateList = mRealEstateViewModel.getRealEstates(USER_ID).getValue();
        Log.d(TAG," list "+mRealEstateViewModel.getRealEstates(USER_ID).getValue());
        this.mRealEstateRecyclerViewAdapter = new RealEstateRecyclerviewAdapter(mRealEstateList,getContext());
        this.mBinding.realEstateListRecyclerView.setAdapter(mRealEstateRecyclerViewAdapter);
    }


    private void updateRealEstateList(List<RealEstate>list){
        this.mRealEstateList.clear();
        this.mRealEstateList.addAll(list);
        mRealEstateRecyclerViewAdapter.notifyDataSetChanged();
    }
}
