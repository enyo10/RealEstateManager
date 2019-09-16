package com.openclassrooms.realestatemanager.management.fragments;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateAddFragment extends BaseFragment {


    public RealEstateAddFragment() {
        // Required empty public constructor
    }




    @Override
    public BaseFragment newInstance() {
        return new RealEstateAddFragment() ;

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_real_estate_add;
    }

    @Override
    protected void configureDesign(View v) {

    }

    @Override
    protected void configureView() {
        setHasOptionsMenu(true);

    }

    @Override
    protected void configureOnclickRecyclerView() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}
