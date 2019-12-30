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
    private static final String TAG = TaxationDialogFragment.class.getName();

    private FragmentTaxationDialogBinding mFragmentTaxationDialogBinding;
    private RealEstateViewModel mRealEstateViewModel;
    private static final double RATE = 0.06;


    public TaxationDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentTaxationDialogBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_taxation_dialog, container, false);
        mFragmentTaxationDialogBinding.setLifecycleOwner(this);

        return mFragmentTaxationDialogBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();


    }

    private void configureViewModel() {
        if (this.getActivity() != null) {
            mRealEstateViewModel = ((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
            mFragmentTaxationDialogBinding.setRealEstateViewModel(mRealEstateViewModel);

            String taxationRate= String.format(getResources().getString(R.string.rate_value),100*RATE,"%");
            String message =String.format(getResources().getString(R.string.two_string),"You have a rate of ",taxationRate);
            mFragmentTaxationDialogBinding.taxationRate.setText(message);

        }

    }

    public void calculateRealEstateRate(double price) {

        int period = new DataConverter().convertStringToInt(
                String.valueOf(mFragmentTaxationDialogBinding.taxationDurationEditText.getText()));
        Log.d(TAG, " price "+period);

        double monthly_tax = Utils.calculateInterestRate(price, RATE, period);
        Log.d(TAG," monthly payment : "+monthly_tax);
        String monthly_tax_to_us_format=DataConverter.formatPriceToDollarFormat(monthly_tax);
        Log.d(TAG, " in US format : "+monthly_tax_to_us_format);
        String ui_text =String.format(getResources().getString(R.string.two_string),monthly_tax_to_us_format,"/month");
        mFragmentTaxationDialogBinding.taxationResult.setText(ui_text);

        Log.d(TAG, " PRICE :" + price + " ,  RATE :" + RATE + " ," + period);


    }



}