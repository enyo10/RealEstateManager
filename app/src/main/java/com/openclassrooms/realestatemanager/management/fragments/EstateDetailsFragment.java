package com.openclassrooms.realestatemanager.management.fragments;


import androidx.fragment.app.Fragment;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstateDetailsFragment extends BaseFragment {


    public EstateDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public BaseFragment newInstance() {
        return new EstateDetailsFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_estate_details;
    }

    @Override
    protected void configureDesign(View v) {

    }

    @Override
    protected void configureView() {

    }

    @Override
    protected void configureOnclickRecyclerView() {

    }


}
