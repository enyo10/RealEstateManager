package com.openclassrooms.realestatemanager.management.realestatedetails;


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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentEstateDetailsBinding;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateDetailsFragment extends Fragment {
    private static final String TAG= EstateDetailsFragment.class.getName();

    private FragmentEstateDetailsBinding mFragmentEstateDetailsBinding;
    private DetailsRecyclerViewAdapter mDetailsRecyclerViewAdapter;
    private RealEstateViewModel mRealEstateViewModel;
    private List<RealEstateImage> mRealEstateImageList;
    private RealEstate mRealEstate;


    public EstateDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentEstateDetailsBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_estate_details,container,false);
        mRealEstateImageList=new ArrayList<>();
        if(this.getActivity()!=null)
        mRealEstateViewModel= ViewModelProviders.of(this.getActivity()).get(RealEstateViewModel.class);


        return mFragmentEstateDetailsBinding.getRoot();

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
        inflater.inflate(R.menu.menu_detail,menu);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAndSetRecyclerViewAdapter();
        mFragmentEstateDetailsBinding.setDataConverter(new DataConverter());

        mRealEstateViewModel.getSelectedRealEstate().observe(this,this::updateUI);

    }

    private void initAndSetRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentEstateDetailsBinding.recyclerViewDetailFragment.setLayoutManager(linearLayoutManager);

        mDetailsRecyclerViewAdapter= new DetailsRecyclerViewAdapter(mRealEstateImageList,this.getContext());
        mFragmentEstateDetailsBinding.recyclerViewDetailFragment.setAdapter(mDetailsRecyclerViewAdapter);
        mFragmentEstateDetailsBinding.setRealEstate(mRealEstate);


    }

    private void updateUI(RealEstate realEstate){
        mRealEstateImageList= Utils.jsonStringToRealEstateImageList(realEstate.getImages());
        Log.i(TAG, " in update method");
        if(mRealEstateImageList!=null)
        Log.d(TAG, " image list size  "+mRealEstateImageList.size());

        mFragmentEstateDetailsBinding.setRealEstate(realEstate);
        mDetailsRecyclerViewAdapter.update(mRealEstateImageList);

        mDetailsRecyclerViewAdapter.notifyDataSetChanged();

        Log.d(TAG," real estate "+realEstate.getType());


    }



}
