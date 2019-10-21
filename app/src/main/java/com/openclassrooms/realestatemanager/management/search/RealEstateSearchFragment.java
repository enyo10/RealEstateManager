package com.openclassrooms.realestatemanager.management.search;


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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.RealEstateSearchDialogBinding;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateSearchFragment extends DialogFragment {
    private static final String TAG= RealEstateSearchFragment.class.getName();
    public static final int USER_ID = 1;

    private RealEstateViewModel mRealEstateViewModel;
    private RealEstateSearchDialogBinding mRealEstateSearchDialogBinding;
    private List<RealEstate>mRealEstateList=new ArrayList<>();


    public RealEstateSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRealEstateSearchDialogBinding = DataBindingUtil.inflate(inflater,R.layout.real_estate_search_dialog, container,
                false);
        mRealEstateSearchDialogBinding.setLifecycleOwner(this);
        return mRealEstateSearchDialogBinding.getRoot();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       init();
    }

    private void init(){
        configureViewModel();
        //getRealEstateItems(USER_ID);

    }

    private void configureViewModel(){
        if(this.getActivity()!=null){
            // rootView =((RealEstateMainActivity) this.getActivity()).mRootView;
            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
            mRealEstateSearchDialogBinding.setRealEstateViewModel(mRealEstateViewModel);
            mRealEstateSearchDialogBinding.setDataConverter(new DataConverter());

        }

    }


    // Get all items for a user
  /*  private void getRealEstateItems(int userId) {
        this.mRealEstateViewModel.getRealEstates(userId).observe(this, this::updateRealEstateItemsList);
    }
*/
    // Update the list of Real Estate item
    private void updateRealEstateItemsList(List<RealEstate> realEstates) {
        mRealEstateList.clear();
        if(SearchResultFragment.getINSTANCE().mRealEstateRecyclerViewAdapter!=null){
        SearchResultFragment.getINSTANCE().mRealEstateRecyclerViewAdapter.updateWithData(realEstates);
        mRealEstateList.addAll(realEstates);
       Log.d(TAG, "updateRealEstateItemsList: data size = "+ mRealEstateList.size());
        }


    }

    /**
     * This method to make search.
     */
    public void startQuery() {

       mRealEstateViewModel.getSearchResult(USER_ID).observe(this, this::updateRealEstateItemsList);
        if(getDialog()!=null)
        getDialog().dismiss();
    }


}
