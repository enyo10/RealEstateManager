package com.openclassrooms.realestatemanager.base;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();


    // Constant
    public String name;


    public BaseFragment() {
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

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =    inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this,view);

        Log.i(TAG, " On create View method.");
        configureView();
        configureOnclickRecyclerView();
        Icepick.restoreInstanceState(this, savedInstanceState);

        return view;
    }





    //Generic activity launcher method
    public void startActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        startActivity(intent);
    }


    //----------------------------//
    // ABSTRACT METHODS
    //----------------------------//

    public abstract  BaseFragment newInstance();

    *//**
     * This method to get the fragment layout resource id.
     * @return id,
     *         the resource id.
     *//*
    protected abstract int getFragmentLayout();

    *//**
     * This method to configure the fragment view.
     *//*
    protected abstract void configureDesign(View v);

    protected abstract void configureView();

    protected abstract void configureOnclickRecyclerView();*/

}
