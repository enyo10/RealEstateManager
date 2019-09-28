package com.openclassrooms.realestatemanager.management.fragments;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseFragment;
import com.openclassrooms.realestatemanager.management.views.RealEstateAdapter;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateListFragment extends BaseFragment{

    @BindView(R.id.real_estate_recycler_view)
    RecyclerView mRecyclerView;
    //@BindView(R.id.typeTextInputLayout)
    TextInputLayout mTypeTextInputLayout;

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
    protected int getFragmentLayout()
    {
       // return R.layout.activity_main;
        return R.layout.fragment_estate_list;
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


      //  this.mAdapter =
        this.mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.mRecyclerView.setLayoutManager(layoutManager);
    }



}
