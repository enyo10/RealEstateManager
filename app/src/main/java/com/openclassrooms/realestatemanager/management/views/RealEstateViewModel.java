package com.openclassrooms.realestatemanager.management.views;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class RealEstateViewModel extends ViewModel {
    private static final String TAG = RealEstateViewModel.class.getName();

    public final MutableLiveData<String>type=new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>>nearbyValues=new MutableLiveData<>();
    public final MutableLiveData<ArrayList<String>>imagesList=new MutableLiveData<ArrayList<String>>();

    public final MutableLiveData<Double> price = new MutableLiveData<>();
    public final MutableLiveData<Integer> numberOfPieces = new MutableLiveData<>();
    public final MutableLiveData<Double> area = new MutableLiveData<>();
    public final MutableLiveData<String>description=new MutableLiveData<>();
    public final MutableLiveData<String> country = new MutableLiveData<>();
    public final MutableLiveData<String> city = new MutableLiveData<>();
    public final MutableLiveData<Integer> zip = new MutableLiveData<>();
    public final MutableLiveData<String> street = new MutableLiveData<>();
    public final MutableLiveData<String> streetNumber = new MutableLiveData<>();
   // public MutableLiveData<ArrayList<RealEstateImage>>realImages=new MutableLiveData<>();
    public MutableLiveData<String>images=new MutableLiveData<>();



    private final MutableLiveData<Boolean>schoolButtonChecked=new MutableLiveData<>();
    private final MutableLiveData<Boolean>hospitalButtonChecked=new MutableLiveData<>();
    private final MutableLiveData<Boolean>busStationButtonChecked=new MutableLiveData<>();
    private final MutableLiveData<Boolean>shoppingCenterButtonChecked=new MutableLiveData<>();
    private final MutableLiveData<Boolean>sportCenterButtonChecked=new MutableLiveData<>();
    private final MutableLiveData<Boolean>parkButtonChecked=new MutableLiveData<>();

    private MutableLiveData<RealEstate> mEstateMutableLiveData ;



    // REPOSITORIES
    private final RealEstateDataRepository realEstateDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;
    private MutableLiveData<Long>insertResult=new MutableLiveData<>();

    // DATA
    @Nullable
    private LiveData<User> currentUser;
    private final MutableLiveData<RealEstate>selectedRealEstate=new MutableLiveData<>();


    public RealEstateViewModel(RealEstateDataRepository realEstateDataSource, UserDataRepository userDataSource, Executor executor) {
        this.realEstateDataSource = realEstateDataSource;
        this.userDataSource = userDataSource;
        this.executor = executor;
        this.insertResult=this.realEstateDataSource.getInsertResult();
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = userDataSource.getUser(userId);
        Log.i(TAG,"current user init "+ currentUser.getValue());
    }

    // -------------
    // FOR USER
    // -------------

    public LiveData<User> getUser(long userId) {
        return this.currentUser;  }

    // -------------
    // FOR ITEM
    // -------------

    public LiveData<List<RealEstate>> getRealEstates(long userId) {
        return realEstateDataSource.getRealEstates(userId);
    }

    public void createRealEstate(RealEstate realEstate) {
        executor.execute(() -> {
            realEstateDataSource.createRealEstate(realEstate);
           // insertResult=realEstateDataSource.getInsertResult();

        });
       // insertResult=realEstateDataSource.getInsertResult();
    }

    public void deleteRealEstate(long realEstateId) {
        executor.execute(() -> {
            realEstateDataSource.deleteRealEstate(realEstateId);
        });
    }

    public void updateRealEstate(RealEstate realEstate) {
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

    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
    }

    public MutableLiveData<RealEstate> getSelectedRealEstate() {
        return selectedRealEstate;
    }

    public void setSelectedRealEstate(RealEstate realEstate){
        this.selectedRealEstate.setValue(realEstate);
    }


    public MutableLiveData<Boolean> getSchoolButtonChecked() {
        Log.i(TAG, " schoolButton  get-> "+schoolButtonChecked.getValue());
        return schoolButtonChecked;
    }

    public void setSchoolButtonChecked(Boolean value) {
        Log.i(TAG," on set " +value);

        if (schoolButtonChecked.getValue() != value) {
            schoolButtonChecked.setValue(value);

        }
    }


    public MutableLiveData<Boolean> getHospitalButtonChecked() {
       Log.i(TAG, " hospital button  get-> "+hospitalButtonChecked.getValue());
        return hospitalButtonChecked;
    }

    public void setHospitalButtonChecked(Boolean value) {
        if(hospitalButtonChecked.getValue()!=value)
            hospitalButtonChecked.setValue(value);

    }

    public MutableLiveData<Boolean> getBusStationButtonChecked() {
        Log.i(TAG, " bus   get-> "+busStationButtonChecked.getValue());
        return busStationButtonChecked;
    }

    public void setBusStationButtonChecked(Boolean value) {
        if (busStationButtonChecked.getValue()!= value) {
            busStationButtonChecked.setValue( value);
        }
    }

    public MutableLiveData<Boolean> getSportCenterButtonChecked() {
        Log.i(TAG, " sport  get: "+sportCenterButtonChecked.getValue());
        return sportCenterButtonChecked;
    }

    public void setSportCenterButtonChecked(Boolean value) {
        if (sportCenterButtonChecked.getValue() != value) {
            sportCenterButtonChecked.setValue( value);

        }
    }

    public MutableLiveData<Boolean> getParkButtonChecked() {
        Log.i(TAG, " park  get : "+parkButtonChecked.getValue());
        return parkButtonChecked;
    }

    public void setParkButtonChecked(Boolean value) {
        if (parkButtonChecked.getValue() != value) {
            parkButtonChecked.setValue(value);

        }
    }

    public MutableLiveData<Boolean> getShoppingCenterButtonChecked() {
        Log.i(TAG, " shopping  get-> "+shoppingCenterButtonChecked.getValue());
        return shoppingCenterButtonChecked;
    }

    public void setShoppingCenterButtonChecked(Boolean value) {
        if (shoppingCenterButtonChecked.getValue()!= value) {
            shoppingCenterButtonChecked.setValue( value);

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

            Integer nbrOfPieces= numberOfPieces.getValue();
            Double surface= area.getValue();
            String desc = description.getValue();
            String countr= country.getValue();
            String cty= city.getValue();
            Integer zp= zip.getValue();
            String str =street.getValue();
            String st_nbr= streetNumber.getValue();
            Double prce= price.getValue();
            String imagedata =images.getValue();



            Address address = new Address(countr, zp, cty, str, st_nbr);

           // RealEstate realEstate=new RealEstate();


        /*RealEstate realEstate = new  RealEstate("userId", Type.HOUSE,price.getValue(), area.getValue(),
                numberOfPieces.getValue()  , "Description", new ArrayList<>(), address,
        Status.UNSOLD, dateOfEntry, dateOfSale);
        */


    RealEstate realEstate= new RealEstate(1, Type.DUPLEX,prce,surface, nbrOfPieces,2,3, desc,images.getValue(),address);

    mEstateMutableLiveData.setValue(realEstate);
    createRealEstate(realEstate);


        }





}

