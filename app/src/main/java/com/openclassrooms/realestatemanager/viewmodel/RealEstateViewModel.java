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
    public MutableLiveData<String>searchArea = new MutableLiveData<>();
    public MutableLiveData<Integer>minRoom = new MutableLiveData<>();
    public MutableLiveData<Integer>maxRoom = new MutableLiveData<>();
    public MutableLiveData<Double>minSurface = new MutableLiveData<>();
    public MutableLiveData<Double>maxSurface = new MutableLiveData<>();
    public MutableLiveData<Double>minPrice = new MutableLiveData<>();
    public MutableLiveData<Double>maxPrice = new MutableLiveData<>();
    public MutableLiveData<String>searchType = new MutableLiveData<>();

    // FOR CAMERA.
    public MutableLiveData<Boolean>hasCamera=new MutableLiveData<>();

    // REPOSITORIES
    private final RealEstateDataRepository realEstateDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;
    private final MutableLiveData<RealEstate> selectedRealEstate = new MutableLiveData<>();

    public MutableLiveData<RealEstate> realEstate=new MutableLiveData<>();
    private MutableLiveData<Long> insertResult;


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

    // Search
   /* public LiveData<List<RealEstate>> searchRealEstate(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                                       Integer minRoom, Integer maxRoom, long userId) {
        return realEstateDataSource.searchRealEstate(type, area, minSurface, maxSurface, minPrice, maxPrice,
                minRoom, maxRoom, userId);
    }*/


    public MutableLiveData<RealEstate> getRealEstate() {

        if (realEstate== null) {
            realEstate = new MutableLiveData<>();
        }
        return realEstate;

    }



    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
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
            }


        }



    }

    public LiveData<List<RealEstate>> getSearchResult(long userId){

        Double surfaceMax = maxSurface.getValue()!=null ? maxSurface.getValue():0.0;
        Double surfaceMin =minSurface.getValue()!=null?minSurface.getValue():0.0;
        Double priceMin = minPrice.getValue()!=null ? minPrice.getValue() :0.0;
        Double priceMax = maxPrice.getValue()!=null ? maxPrice.getValue() :0.0;
        String type = searchType.getValue()!=null ? searchType.getValue():"";
        String area = searchArea.getValue()!=null? searchArea.getValue() :"";
        Integer roomMin = minRoom.getValue()!=null? minRoom.getValue() :0;
        Integer roomMax = maxRoom.getValue()!=null ? maxRoom.getValue() :0;

        Log.d(TAG, " minRoom " +minRoom.getValue());
        Log.d(TAG, " maxRoom " +maxRoom.getValue());
        Log.d(TAG, " minSurface " +minSurface.getValue());
        Log.d(TAG, " maxSurface " +maxSurface.getValue());
        Log.d(TAG, " minPrice " +minPrice.getValue());
        Log.d(TAG, " maxPrice " +maxPrice.getValue());

        Log.d(TAG, " SearchArea " +searchArea.getValue());

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
            queryString+="AND price < ?";
            args.add(priceMax);
        }



       // String area = searchArea.getValue();
       /* double surfaceMin = minSurface.getValue();
        double surfaceMax = maxSurface.getValue();
        double priceMin = minPrice.getValue();
        double priceMax = maxPrice.getValue();
        int roomMin = minRoom.getValue();
        int roomMax = maxRoom.getValue();

*/

/*

        String type = searchType.getValue()!=null ? searchType.getValue() : "%";
        String area = searchArea.getValue()!=null? searchArea.getValue() : "%";
        String surfaceMin = minSurface.getValue()!=null ? minSurface.getValue().toString() : "0";
        String surfaceMax = maxSurface.getValue()!=null ? maxSurface.getValue().toString() : "100000";
        String priceMin = minPrice.getValue()!=null ? minPrice.getValue().toString() : "0";
        String priceMax = maxPrice.getValue()!=null ? maxPrice.getValue().toString() : "999999999";
        String roomMin = minRoom.getValue()!=null? minRoom.getValue().toString() : "0";
        String roomMax = maxRoom.getValue()!=null ? maxRoom.getValue().toString() : "100";
*/

      /*return   this.searchRealEstate(type, area, Integer.valueOf(surfaceMin), Integer.valueOf(surfaceMax), Long.valueOf(priceMin),Long.valueOf(priceMax),
                Integer.valueOf(roomMin), Integer.valueOf(roomMax), userId);
*//*
        return   this.realEstateDataSource.rawQuerySearch(type, area, surfaceMin, surfaceMax, priceMin,priceMax,
                roomMin, roomMax, userId);

*/


        Log.d(TAG, " query : " + queryString);
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        Log.d(TAG, " final query "+query.getSql());
        Log.d(TAG, " to array : " +args.toString());

         return this.realEstateDataSource.rawQuerySearch(query);
    }


}

