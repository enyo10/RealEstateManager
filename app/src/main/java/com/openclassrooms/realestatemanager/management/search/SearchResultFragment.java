package com.openclassrooms.realestatemanager.management.search;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.management.realestatelist.EstateListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends EstateListFragment {
    private static final String TAG=SearchResultFragment.class.getName();
    private static SearchResultFragment INSTANCE;


    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment getINSTANCE(){
        if(INSTANCE==null)
            INSTANCE=new SearchResultFragment();
        return INSTANCE;


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


    protected void getRealEstateItems(long userId) {
        this.mRealEstateViewModel.getSearchResult(userId).observe(this, this::updateRealEstateList);

    }


}
