package com.openclassrooms.realestatemanager.viewmodel;


import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class RealEstateViewModel extends ViewModel {
    private static final String TAG = RealEstateViewModel.class.getName();

  //  public String type;
    public ArrayList<String> nearbyValues=new ArrayList<>();


    //  FOR SEARCH DATA.
    public MutableLiveData<Integer>minRoom = new MutableLiveData<>();
    public MutableLiveData<Integer>maxRoom = new MutableLiveData<>();
    public MutableLiveData<Double>minSurface = new MutableLiveData<>();
    public MutableLiveData<Double>maxSurface = new MutableLiveData<>();
    public MutableLiveData<Double>minPrice = new MutableLiveData<>();
    public MutableLiveData<Double>maxPrice = new MutableLiveData<>();
    public MutableLiveData<String>searchType = new MutableLiveData<>();
    public MutableLiveData<Date>beginPeriodDate=new MutableLiveData<>();
    public MutableLiveData<Date>endPeriodDate=new MutableLiveData<>();
    public MutableLiveData<String>location=new MutableLiveData<>();

    // FOR CAMERA.
    public MutableLiveData<Boolean>hasCamera=new MutableLiveData<>();

    // REPOSITORIES
    private final RealEstateDataRepository realEstateDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;
    private final MutableLiveData<RealEstate> selectedRealEstate = new MutableLiveData<>();

    public MutableLiveData<RealEstate> realEstate=new MutableLiveData<>();
    private MutableLiveData<Long> insertResult;
    private MutableLiveData<Integer>updateResult;
    private boolean actionUpdate;



    private String realEstateImagesString;

    public String getRealEstateImagesString() {
        return realEstateImagesString;
    }

    public void setRealEstateImagesString(String realEstateImagesString) {
        this.realEstateImagesString = realEstateImagesString;
    }

    // DATA
    @Nullable
    private LiveData<User> currentUser;



    public RealEstateViewModel(RealEstateDataRepository realEstateDataSource, UserDataRepository userDataSource, Executor executor) {
        this.realEstateDataSource = realEstateDataSource;
        this.userDataSource = userDataSource;
        this.executor = executor;
        this.insertResult = this.realEstateDataSource.getInsertResult();
        this.updateResult=this.realEstateDataSource.getUpdateResult();
    }

    public void init(long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = userDataSource.getUser(userId);
        Log.i(TAG, "current user init " + currentUser.getValue());
    }

    // -------------
    // FOR USER
    // -------------

    public LiveData<User> getUser(long userId) {
        return this.currentUser;
    }

    // -------------
    // FOR ITEM
    // -------------

    public LiveData<List<RealEstate>> getRealEstates(long userId) {
        return realEstateDataSource.getRealEstates(userId);
    }

    public void createRealEstate(RealEstate realEstate) {
        executor.execute(() -> {
            realEstateDataSource.createRealEstate(realEstate);

        });
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

        if (realEstate== null) {
            realEstate = new MutableLiveData<>();
        }
        return realEstate;

    }

    public boolean isActionUpdate() {
        return actionUpdate;
    }

    public void setActionUpdate(boolean actionUpdate) {
        this.actionUpdate = actionUpdate;
    }

    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
    }
    public MutableLiveData<Integer>getUpdateResult(){ return updateResult;
    }

    public MutableLiveData<RealEstate> getSelectedRealEstate() {
        return selectedRealEstate;
    }

    public void setSelectedRealEstate(RealEstate realEstate) {
        this.selectedRealEstate.setValue(realEstate);
    }

    /**
     * this method to save the RealEstate object in the data base.
     * , the view clicked to fire the action save.
     */
    public void onRealEstateSave(long userId) {

        RealEstate myRealEstate =realEstate.getValue();
        Log.d(TAG, " In View model save.");


        if(myRealEstate!=null) {
            Log.i(TAG, " Real Estate not null");

            myRealEstate.setImages(getRealEstateImagesString());
            myRealEstate.setNearbyPointOfInterest(nearbyValues);

            Log.i(TAG, "Real : "+myRealEstate.toString());
           // if id==0, we create the new real estate, else we update.
            if(myRealEstate.getId()==0) {
                myRealEstate.setDateOfEntry(new Date());

                createRealEstate(myRealEstate);
            }else {
                updateRealEstate(myRealEstate);
                Log.d(TAG, " update success  " +myRealEstate.isSold());
            }


        }



    }

    public LiveData<List<RealEstate>> getSearchResult(long userId){

        Double surfaceMax = maxSurface.getValue()!=null ? maxSurface.getValue():0.0;
        Double surfaceMin =minSurface.getValue()!=null ?minSurface.getValue():0.0;
        Double priceMin = minPrice.getValue()!=null ? minPrice.getValue() :0.0;
        Double priceMax = maxPrice.getValue()!=null ? maxPrice.getValue() :0.0;
        String type = searchType.getValue()!=null ? searchType.getValue():"";
        String searchLocation = location.getValue()!=null? location.getValue() :"";
        Integer roomMin = minRoom.getValue()!=null? minRoom.getValue() :0;
        Integer roomMax = maxRoom.getValue()!=null ? maxRoom.getValue() :0;
        Date searchPeriodBeginDate = beginPeriodDate.getValue();
        Date searchPeriodEndDate = endPeriodDate.getValue();

        Log.d(TAG, " minRoom " +minRoom.getValue());
        Log.d(TAG, " maxRoom " +maxRoom.getValue());
        Log.d(TAG, " minSurface " +minSurface.getValue());
        Log.d(TAG, " maxSurface " +maxSurface.getValue());
        Log.d(TAG, " minPrice " +minPrice.getValue());
        Log.d(TAG, " maxPrice " +maxPrice.getValue());

        Log.d(TAG, " SearchArea " +location.getValue());

        // Query string
       // String queryString =new String();
       String  queryString = "SELECT *FROM RealEstate WHERE userId =?";
        // List of bind parameters
        List<Object>args=new ArrayList<>();
        args.add(userId);

        if(!TextUtils.isEmpty(type)){
            queryString +=" AND type LIKE ?";
            args.add(type.toUpperCase());
            Log.d(TAG, " search type " +type.toUpperCase());

        }
        if(roomMin!=0){
            queryString+=" AND numberOfRooms > ? ";
            args.add(roomMin);
        }
        if(roomMax!=0){
            queryString+="AND numberOfRooms < ?";
            args.add(roomMax);
        }

        if(surfaceMin!=0){
            queryString+=" AND surface > ? ";
            args.add(surfaceMin);
        }
        if(surfaceMax!=0){
            queryString+="AND surface < ?";
            args.add(surfaceMax);
        }

        if(priceMin!=0){
            queryString+=" AND price > ? ";
            args.add(priceMin);
        }
        if(priceMax!=0){
            queryString+="AND price < ? ";
            args.add(priceMax);
        }
        if(searchPeriodBeginDate!=null){
            queryString+="AND dateOfEntry > ? ";
            args.add(searchPeriodBeginDate);
        }

        if(searchPeriodEndDate!=null){
            queryString+="AND dateOfEntry < ? ";
            args.add(searchPeriodEndDate);
        }

        if(!TextUtils.isEmpty(searchLocation)){
           // queryString+="AND city ";
            queryString+="AND city COLLATE SQL_Latin1_General_CP1_CI_AS LIKE ? ";
            args.add(searchLocation);

           // LIKE '%cats%'
        }



        Log.d(TAG, " query : " + queryString);
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        Log.d(TAG, " final query "+query.getSql());
        Log.d(TAG, " to array : " +args.toString());

         return this.realEstateDataSource.rawQuerySearch(query);
    }


}

