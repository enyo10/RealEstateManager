package com.openclassrooms.realestatemanager.management.fragments;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseFragment;
import com.openclassrooms.realestatemanager.management.views.RealEstateAdapter;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateListFragment extends BaseFragment {

   /* @BindView(R.id.real_estate_recycler_view)
    RecyclerView mRecyclerView;*/

    private RealEstateAdapter mAdapter;
    private List<RealEstate> mRealEstateList;



    public EstateListFragment() {
        // Required empty public constructor
    }

    @Override
    public BaseFragment newInstance() {
        return new EstateListFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void configureDesign(View v) {

    }

    @Override
    protected void configureView() {
        configureRecyclerView();

    }

    @Override
    protected void configureOnclickRecyclerView() {

    }


    private void configureRecyclerView(){


        /*this.mAdapter = new RealEstateAdapter(mRealEstateList, Glide.with(this),getContext());
        this.mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.mRecyclerView.setLayoutManager(layoutManager);*/
    }


}
