package com.openclassrooms.realestatemanager.management.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.base.BaseActivity;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.models.NearbyPOI;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class RealEstateRegistrationActivity extends BaseActivity {
    private static final String TAG = RealEstateRegistrationActivity.class.getName();

    private static int USER_ID = 1;
    private RealEstateViewModel mRealEstateViewModel;

    private String mType;
    private ArrayList<String>list=new ArrayList<>();
    private View rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolbar();

        // 8 - ViewModel
        this.configureViewModel();

        // 9 - Get current user & items from Database
        this.getCurrentUser(USER_ID);
        this.getItems(USER_ID);
    }
    // 3 - Get all items for a user
    private void getItems(int userId){
        this.mRealEstateViewModel.getRealEstates(userId).observe(this, this::updateItemsList);
    }
    // 6 - Update the list of items
    private void updateItemsList(List<RealEstate> realEstates)
    {
        //this.adapter.updateData(reelEstates);
    }




    @Override
    public int getActivityLayout() {
        return R.layout.activity_real_estate;
    }

    @Override
    public void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mRealEstateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RealEstateViewModel.class);
        this.mRealEstateViewModel.init(USER_ID);

    }

    @Override
    public void configureView() {
        // Default Type is House.
        mType = Type.PENTHOUSE;
        rootView=findViewById(android.R.id.content);

    }


    @OnClick({R.id.duplex_button, R.id.loft_button, R.id.penthouse_button, R.id.manor_button})
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.duplex_button:
                if (checked)
                    mType = Type.DUPLEX;
                break;

            case R.id.penthouse_button:
                if (checked)
                    mType = Type.PENTHOUSE;
                break;

            case R.id.manor_button:
                if (checked)
                    mType = Type.MANOR;
                break;
            case R.id.loft_button:
                if (checked)
                    mType = Type.LOFT;
                break;
        }
        Log.d(TAG, " Estate type : " + mType);
    }


    @OnClick({R.id.school, R.id.hospital, R.id.shopping_center, R.id.bus_station, R.id.park, R.id.sport_center})
    public void onCheckboxClicked(View view) {

        // ArrayList<String> list=new ArrayList();
        // Is the view now checked?
        boolean checked = ((MaterialCheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {

            case R.id.hospital:
                if (checked) {
                    list.add(NearbyPOI.HOSPITAL);
                    Log.d(TAG, " checked box -- " + R.id.hospital);
                } else {
                    list.remove(NearbyPOI.HOSPITAL);
                    Log.d(TAG, " removed -- " + NearbyPOI.HOSPITAL);
                }
                break;

            case R.id.school:
                if (checked) {
                    // mRealEstate.addNearbyPointOfInterest(NearbyPOI.SCHOOL);
                    Log.d(TAG, " checked box -- " + NearbyPOI.SCHOOL);
                    list.add(NearbyPOI.SCHOOL);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SCHOOL);
                    list.remove(NearbyPOI.SCHOOL);
                }
                break;

            case R.id.park:
                if (checked) {

                    Log.d(TAG, " checked box -- " + NearbyPOI.PARK);
                    list.add(NearbyPOI.PARK);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.PARK);
                    list.remove(NearbyPOI.PARK);
                }


                break;
            case R.id.bus_station:
                if (checked) {
                    //   mRealEstate.addNearbyPointOfInterest(NearbyPOI.BUS_STATION);
                    Log.d(TAG, " checked box -- " + NearbyPOI.BUS_STATION);
                    list.add(NearbyPOI.BUS_STATION);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.BUS_STATION);
                    list.remove(NearbyPOI.BUS_STATION);
                }
                break;
            case R.id.shopping_center:
                if (checked) {
                    Log.d(TAG, " checked box -- " + NearbyPOI.SHOPPING_CENTER);
                    // mRealEstate.addNearbyPointOfInterest(NearbyPOI.SHOPPING_CENTER);
                    list.add(NearbyPOI.SHOPPING_CENTER);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SHOPPING_CENTER);
                    list.remove(NearbyPOI.SHOPPING_CENTER);
                }
                break;

            case R.id.sport_center:
                if (checked) {
                    //  mRealEstate.addNearbyPointOfInterest(NearbyPOI.SPORT_CENTER);
                    Log.d(TAG, " checked box -- " + NearbyPOI.SPORT_CENTER);
                    list.add(NearbyPOI.SPORT_CENTER);
                } else {
                    Log.d(TAG, " removed -- " + NearbyPOI.SPORT_CENTER);
                    list.add(NearbyPOI.SPORT_CENTER);
                }

                break;


        }
        Log.i(TAG, " " + list.toString());
    }

    @OnClick(R.id.estate_save_button)
    public void getTextValues() {

        final List<TextInputLayout> textInputLayouts = ViewUtils.findViewsWithType(
                rootView, TextInputLayout.class);

        boolean noErrors = true;
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_string));
                noErrors = false;

            } else {
                textInputLayout.setError(null);
            }
        }

        if (noErrors) {
            // All fields are valid!
            int numberOfPieces = Integer.valueOf(( (TextInputEditText) findViewById(R.id.real_estate_pieces_number_text_edit)).getText().toString());
            double area = Double.valueOf(( (TextInputEditText) findViewById(R.id.real_estate_surface_text_edit)).getText().toString());
            String description = ( (TextInputEditText) findViewById(R.id.real_estate_description_edit_text)).getText().toString();
            String  city= ( (TextInputEditText) findViewById(R.id.real_estate_city_edit_text)).getText().toString();
            String  country= ( (TextInputEditText) findViewById(R.id.real_estate_country_text_edit)).getText().toString();
            String  street= ( (TextInputEditText) findViewById(R.id.real_estate_street_edit_text)).getText().toString();
            int zip = Integer.valueOf(( (TextInputEditText) findViewById(R.id.real_estate_zip_edit_text)).getText().toString());
            double price = Double.valueOf(( (TextInputEditText) findViewById(R.id.real_estate_price_edit_text)).getText().toString());

            Log.d(TAG, " numberOfPieces: "+numberOfPieces);
            Log.d(TAG, " area "+area);
            Log.d(TAG, " description: "+description);
            Log.d(TAG, " city: "+city);
            Log.d(TAG, " country: "+country);
            Log.d(TAG, " street: "+street);
            Log.d(TAG, " zip: "+zip);
            Log.d(TAG, " price: "+price);

        }

    }


    // 3 - Get Current User
    private void getCurrentUser(int userId){
        this.mRealEstateViewModel.getUser(userId).observe(this, this::updateHeader);
    }

    private void updateHeader(User user){
       // this.profileText.setText(user.getUsername());
       // Glide.with(this).load(user.getUrlPicture()).apply(RequestOptions.circleCropTransform()).into(this.profileImage);
    }


}