package com.openclassrooms.realestatemanager.management.realestatedetails;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        mRealEstateViewModel.getSelectedRealEstate().observe(this, realEstate -> {
            Log.i(TAG, "Real estate changed");
            mRealEstate=realEstate;
            updateUI(mRealEstate);

        });

        return mFragmentEstateDetailsBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initAndSetRecyclerViewAdapter();

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
        List<RealEstateImage> list=new ArrayList<>();
        Map<String,String>map=null;
        try {
            map=Utils.jsonToMap(realEstate.getImages());

        }catch (Exception e){
             e.printStackTrace();
        }

        if(map!=null){

            Set<String> keySet=map.keySet();

            for(String k:keySet){
                list.add(new RealEstateImage(k,map.get(k)));

            }
        }
        mRealEstateImageList.clear();
        mRealEstateImageList.containsAll(list);
        mDetailsRecyclerViewAdapter.notifyDataSetChanged();


    }




    


}
