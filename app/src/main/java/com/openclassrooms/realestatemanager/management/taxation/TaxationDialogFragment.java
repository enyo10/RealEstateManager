package com.openclassrooms.realestatemanager.management.taxation;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentTaxationDialogBinding;
import com.openclassrooms.realestatemanager.management.activities.RealEstateMainActivity;
import com.openclassrooms.realestatemanager.utils.DataConverter;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodel.RealEstateViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaxationDialogFragment extends DialogFragment {
    private static final String TAG=TaxationDialogFragment.class.getName();

   private FragmentTaxationDialogBinding mFragmentTaxationDialogBinding;
   private RealEstateViewModel mRealEstateViewModel;
   private static final double RATE=0.06;


    public TaxationDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentTaxationDialogBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_taxation_dialog,container,false);
        mFragmentTaxationDialogBinding.setLifecycleOwner(this);

        return mFragmentTaxationDialogBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();


    }

    private void configureViewModel(){
        if(this.getActivity()!=null){
       // View   rootView =((RealEstateMainActivity) this.getActivity()).mRootView;
            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
           mFragmentTaxationDialogBinding.setRealEstateViewModel(mRealEstateViewModel);

        }

    }

    public void calculateRealEstateRate(double price){

        int period = new DataConverter().convertStringToInt(
                String.valueOf(mFragmentTaxationDialogBinding.taxationDurationEditText.getText()));

       double monthly_tax= Utils.calculateInterestRate(price,RATE,period);
       mFragmentTaxationDialogBinding.taxationResult.setText(DataConverter.formatPriceToDollarFormat(monthly_tax));

    Log.d(TAG," PRICE :" +price +" ,  RATE :"+ RATE +" ,"+period);



    }




}
