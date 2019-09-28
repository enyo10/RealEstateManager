package com.openclassrooms.realestatemanager.management.views;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.RealEstateImage;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class RealEstateViewModel extends ViewModel {
    private static final String TAG = RealEstateViewModel.class.getName();
    public MutableLiveData<String>type=new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>>nearbyValues=new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>>imagesList=new MutableLiveData<ArrayList<String>>();

    public MutableLiveData<Double> price = new MutableLiveData<>();
    public MutableLiveData<Integer> numberOfPieces = new MutableLiveData<>();
    public MutableLiveData<Double> area = new MutableLiveData<>();
    public MutableLiveData<String>description=new MutableLiveData<>();
    public MutableLiveData<String> country = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();
    public MutableLiveData<Integer> zip = new MutableLiveData<>();
    public MutableLiveData<String> street = new MutableLiveData<>();
    public MutableLiveData<String> streetNumber = new MutableLiveData<>();
    public MutableLiveData<ArrayList<RealEstateImage>>realImages=new MutableLiveData<>();

    private MutableLiveData<RealEstate> mEstateMutableLiveData ;

    private MutableLiveData<Boolean>schoolButtonChecked=new MutableLiveData<>();
    private MutableLiveData<Boolean>hospitalButtonChecked=new MutableLiveData<>();
    private MutableLiveData<Boolean>busStationButtonChecked=new MutableLiveData<>();
    private MutableLiveData<Boolean>shoppingCenterButtonChecked=new MutableLiveData<>();
    private MutableLiveData<Boolean>sportCenterButtonChecked=new MutableLiveData<>();
    private MutableLiveData<Boolean>parkButtonChecked=new MutableLiveData<>();


    // REPOSITORIES
    private final RealEstateDataRepository realEstateDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<User> currentUser;

    public RealEstateViewModel(RealEstateDataRepository realEstateDataSource, UserDataRepository userDataSource, Executor executor) {
        this.realEstateDataSource = realEstateDataSource;
        this.userDataSource = userDataSource;
        this.executor = executor;
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = userDataSource.getUser(userId);
    }

    // -------------
    // FOR USER
    // -------------

    public LiveData<User> getUser(long userId) { return this.currentUser;  }

    // -------------
    // FOR ITEM
    // -------------

    public LiveData<List<RealEstate>> getRealEstates(long userId) {
        return realEstateDataSource.getRealEstates(userId);
    }

    public void createItem(RealEstate realEstate) {
        executor.execute(() -> {
            realEstateDataSource.createRealEstate(realEstate);
        });
    }

    public void deleteItem(long realEstateId) {
        executor.execute(() -> {
            realEstateDataSource.deleteRealEstate(realEstateId);
        });
    }

    public void updateItem(RealEstate realEstate) {
        executor.execute(() -> {
            realEstateDataSource.updateRealEstate(realEstate);
        });
    }


    public MutableLiveData<RealEstate> getRealEstate() {

        if (mEstateMutableLiveData == null) {
            mEstateMutableLiveData = new MutableLiveData<>();
        }
        return mEstateMutableLiveData;

    }


   /* *//**
     * this method to keep track of selected view ( the type of Real estate  in our case)
     * @param , the view selected
     *//*
    public void onRealEstateTypeChanged(View view){
        Button b= (Button) view;
       String tag= view.getTag().toString();
       this.type.setValue(tag);
       Log.i(TAG," view tag "+tag);
       Log.i(TAG, "view by name " +b.getText());

    }*/



    public MutableLiveData<Boolean> getSchoolButtonChecked() {
        Log.i(TAG, " schoolButton  get-> "+schoolButtonChecked.getValue());
        return schoolButtonChecked;
    }

    public void setSchoolButtonChecked(MutableLiveData<Boolean> value) {
        Log.i(TAG," on set " +value);

        if (schoolButtonChecked != value) {
            schoolButtonChecked = value;

        }
    }

    public MutableLiveData<Boolean> getHospitalButtonChecked() {
        Log.i(TAG, " schoolButton  get-> "+hospitalButtonChecked.getValue());
        return hospitalButtonChecked;
    }

    public void setHospitalButtonChecked(MutableLiveData<Boolean> value) {
        if (hospitalButtonChecked != value) {
            hospitalButtonChecked = value;

        }
    }

    public MutableLiveData<Boolean> getBusStationButtonChecked() {
        Log.i(TAG, " bus   get-> "+busStationButtonChecked.getValue());
        return busStationButtonChecked;
    }

    public void setBusStationButtonChecked(MutableLiveData<Boolean> value) {
        if (busStationButtonChecked!= value) {
            busStationButtonChecked= value;

        }
    }



    public MutableLiveData<Boolean> getSportCenterButtonChecked() {
        Log.i(TAG, " sport  get-> "+schoolButtonChecked.getValue());
        return sportCenterButtonChecked;
    }

    public void setSportCenterButtonChecked(MutableLiveData<Boolean> value) {
        if (schoolButtonChecked != value) {
            schoolButtonChecked = value;

        }
    }

    public MutableLiveData<Boolean> getParkButtonChecked() {
        Log.i(TAG, " park  get-> "+schoolButtonChecked.getValue());
        return parkButtonChecked;
    }

    public void setParkButtonChecked(MutableLiveData<Boolean> value) {
        if (parkButtonChecked != value) {
            parkButtonChecked = value;

        }
    }

    public MutableLiveData<Boolean> getShoppingCenterButtonChecked() {
        Log.i(TAG, " shopping  get-> "+schoolButtonChecked.getValue());
        return shoppingCenterButtonChecked;
    }

    public void setShoppingCenterButtonChecked(MutableLiveData<Boolean> value) {
        if (shoppingCenterButtonChecked!= value) {
            shoppingCenterButtonChecked = value;

        }
    }

    /**
         * this method to save the RealEstate object in the data base.
         , the view clicked to fire the action save.
         */
        public void onRealEstateSave (){
            Date dateOfEntry = new Date();
            Date dateOfSale = null;
            Log.i(TAG, " type -> " + type.getValue());

            Log.d(TAG, "Number of pieces " + numberOfPieces.getValue());
            Log.d(TAG, "Area  " + area.getValue());
            Log.d(TAG, " Description " + description.getValue());
            Log.d(TAG, " Country : " + country.getValue());
            Log.d(TAG, " City " + city.getValue());
            Log.d(TAG, " Zip :" + zip.getValue());
            Log.d(TAG, " Street :" + street.getValue());
            Log.d(TAG, " Street number :" + streetNumber.getValue());
            Log.d(TAG, "Price " + price.getValue());
           // if(imagesList.getValue()!=null)
            Log.i(TAG," ArrayList : " +imagesList.getValue().toString() );


            Address address = new Address(country.getValue(), 1, city.getValue(), street.getValue(), streetNumber.getValue());

       /* RealEstate realEstate = new  RealEstate("userId", Type.HOUSE,price.getValue(), area.getValue(),
                numberOfPieces.getValue()  , "Description", new ArrayList<>(), address,
        Status.UNSOLD, dateOfEntry, dateOfSale);
*/

            //mEstateMutableLiveData.setValue(realEstate);


        }





}

