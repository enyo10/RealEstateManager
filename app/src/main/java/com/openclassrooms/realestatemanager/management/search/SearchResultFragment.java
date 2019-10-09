package com.openclassrooms.realestatemanager.management.search;


import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.management.realestatelist.EstateListFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends EstateListFragment {
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }



// Get all RealEstate for a given  user id.
/*protected void getRealEstateItems(long userId) {
    this.mRealEstateViewModel.getRealEstates(userId).observe(this, this::updateRealEstateList);
}*/



}
