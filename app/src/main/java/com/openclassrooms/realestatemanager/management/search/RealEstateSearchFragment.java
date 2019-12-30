package com.openclassrooms.realestatemanager.management.search;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class RealEstateSearchFragment extends DialogFragment implements View.OnClickListener{
    private static final String TAG= RealEstateSearchFragment.class.getName();
    public static final int USER_ID = 1;

    private RealEstateViewModel mRealEstateViewModel;
    private RealEstateSearchDialogBinding mRealEstateSearchDialogBinding;
    private List<RealEstate>mRealEstateList=new ArrayList<>();

    private DatePickerDialog.OnDateSetListener mEntryDateSetListener, mSoldDateSetListener;
    private Date startDate, endDate;

    private DatePickerDialog  mStartPeriodPickerDialog;
    private DatePickerDialog  mEndPeriodPickerDialog;
    private SimpleDateFormat displayDateFormatter;
    private Calendar newCalendar;


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
        mRealEstateSearchDialogBinding.searchEndPeriodButton.setOnClickListener(this);
        mRealEstateSearchDialogBinding.searchBeginPeriodButton.setOnClickListener(this);

       init();
    }

    private void init(){
        configureViewModel();
        manageDateFields();

    }

    private void configureViewModel(){
        if(this.getActivity()!=null){
            mRealEstateViewModel=((RealEstateMainActivity) this.getActivity()).mRealEstateViewModel;
            mRealEstateSearchDialogBinding.setRealEstateViewModel(mRealEstateViewModel);
            mRealEstateSearchDialogBinding.setDataConverter(new DataConverter());

        }

    }


    // Update the list of Real Estate item
    private void updateRealEstateItemsList(List<RealEstate> realEstates) {
        mRealEstateList.clear();
        if(SearchResultFragment.getINSTANCE().mRealEstateRecyclerViewAdapter!=null){
        SearchResultFragment.getINSTANCE().mRealEstateRecyclerViewAdapter.updateWithData(realEstates);
        mRealEstateList.addAll(realEstates);
       Log.d(TAG, "updateRealEstateItemsList: data size = "+ mRealEstateList.size());
        }


    }

    // -------------------
    // MANAGE DATE FIELDS
    // -------------------
    private void manageDateFields() {
        newCalendar = Calendar.getInstance();
        displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setBeginPeriodDate();
        setEndPeriodDate();
    }



    public void setBeginPeriodDate() {

        // Create a DatePickerDialog and manage it
        mStartPeriodPickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mRealEstateViewModel.beginPeriodDate.setValue(newDate.getTime());

                // Display date selected
               mRealEstateSearchDialogBinding.searchBeginPeriodButton.setText( displayDateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        mStartPeriodPickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.i(TAG," Date is cancel ");
                    mRealEstateViewModel.beginPeriodDate.setValue(null);
                }
            }
        });
    }

    private void setEndPeriodDate() {
        // Create a DatePickerDialog and manage it
        mEndPeriodPickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);

                mRealEstateViewModel.endPeriodDate.setValue(newDate.getTime());

            }


        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mEndPeriodPickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    Log.i(TAG," Date is cancel ");
                    mRealEstateViewModel.endPeriodDate.setValue(null);
                }
            }
        });
    }


    /**
     * This method to make search.
     */
    public void startQuery() {

       mRealEstateViewModel.getSearchResult(USER_ID).observe(this, this::updateRealEstateItemsList);
        if(getDialog()!=null)
        getDialog().dismiss();
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.search_begin_period_button)
            this.mStartPeriodPickerDialog.show();
        if(v.getId()==R.id.search_end_period_button)
            this.mEndPeriodPickerDialog.show();

    }
}
