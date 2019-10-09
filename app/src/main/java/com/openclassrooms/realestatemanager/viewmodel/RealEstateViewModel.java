package com.openclassrooms.realestatemanager.viewmodel;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public String type;
    public ArrayList<String> nearbyValues=new ArrayList<>();

    public final MutableLiveData<Integer> numberOfRooms = new MutableLiveData<>();
    public final MutableLiveData<Integer> numberOfBedRooms = new MutableLiveData<>();
    public final MutableLiveData<Integer> numberOfBathRooms = new MutableLiveData<>();
    public final MutableLiveData<Double> surface = new MutableLiveData<>();
    public final MutableLiveData<String> description = new MutableLiveData<>();
    public final MutableLiveData<String> country = new MutableLiveData<>();
    public final MutableLiveData<String> city = new MutableLiveData<>();
    public final MutableLiveData<String> zip = new MutableLiveData<>();
    public final MutableLiveData<String> street = new MutableLiveData<>();
    public final MutableLiveData<String> streetNumber = new MutableLiveData<>();
    public final MutableLiveData<Double>realPrice=new MutableLiveData<>();




    //  FOR SEARCH DATA.
    public MutableLiveData<String>searchArea = new MutableLiveData<>();
    public MutableLiveData<Integer>minRoom = new MutableLiveData<>();
    public MutableLiveData<Integer>maxRoom = new MutableLiveData<>();
    public MutableLiveData<Integer>minSurface = new MutableLiveData<>();
    public MutableLiveData<Integer>maxSurface = new MutableLiveData<>();
    public MutableLiveData<Long>minPrice = new MutableLiveData<>();
    public MutableLiveData<Long>maxPrice = new MutableLiveData<>();
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


    private String setRealEstateImagesString;

    public String getSetRealEstateImagesString() {
        return setRealEstateImagesString;
    }

    public void setSetRealEstateImagesString(String setRealEstateImagesString) {
        this.setRealEstateImagesString = setRealEstateImagesString;
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
    public LiveData<List<RealEstate>> searchRealEstate(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                                       Integer minRoom, Integer maxRoom, long userId) {
        return realEstateDataSource.searchRealEstate(type, area, minSurface, maxSurface, minPrice, maxPrice,
                minRoom, maxRoom, userId);
    }


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

       // String aType=type.getValue();
        Integer nbrOfRooms = numberOfRooms.getValue()!=null?numberOfRooms.getValue():0;
        Integer nbrOfBathRoom = numberOfBathRooms.getValue()!=null?numberOfBathRooms.getValue():0;
        Integer nbrOfBedRoom = numberOfBedRooms.getValue()!=null?numberOfBedRooms.getValue():0;
        Double aSurface = surface.getValue()!=null?surface.getValue():0.0;
        String desc = description.getValue();
        String myCountry = country.getValue();
        String cty = city.getValue();
        String zp = zip.getValue();
        String str = street.getValue();
        String st_nbr = streetNumber.getValue();
        Double aPrice = realPrice.getValue()!=null?realPrice.getValue():0.0;



       // Address address = new Address(myCountry, zp, cty, str, st_nbr);

       // RealEstate myRealEstate = new RealEstate(1, type, aPrice, aSurface,nbrOfRooms,nbrOfBedRoom, nbrOfBathRoom, desc,getSetRealEstateImagesString(), address,nearbyValues);
        RealEstate myRealEstate =realEstate.getValue();


        if(myRealEstate!=null) {
            Log.i(TAG, " Real Estate not null");
            myRealEstate.setDateOfEntry(new Date());
            myRealEstate.setImages(getSetRealEstateImagesString());
            myRealEstate.setNearbyPointOfInterest(nearbyValues);

            myRealEstate.setUserId(userId);
            Log.i(TAG, "Real : "+myRealEstate.toString());

            createRealEstate(myRealEstate);}


    }

    public LiveData<List<RealEstate>> getSearchResult(long userId){

        String type = searchType.getValue()!=null ? searchType.getValue() : "%";
        String area = searchArea.getValue()!=null? searchArea.getValue() : "%";
        String surfaceMin = minSurface.getValue()!=null ? minSurface.getValue().toString() : "0";
        String surfaceMax = maxSurface.getValue()!=null ? maxSurface.getValue().toString() : "100000";
        String priceMin = minPrice.getValue()!=null ? minPrice.getValue().toString() : "0";
        String priceMax = maxPrice.getValue()!=null ? maxPrice.getValue().toString() : "999999999";
        String roomMin = minRoom.getValue()!=null? minRoom.getValue().toString() : "0";
        String roomMax = maxRoom.getValue()!=null ? maxRoom.getValue().toString() : "100";

      return   this.searchRealEstate(type, area, Integer.valueOf(surfaceMin), Integer.valueOf(surfaceMax), Long.valueOf(priceMin),Long.valueOf(priceMax),
                Integer.valueOf(roomMin), Integer.valueOf(roomMax), userId);


    }


}

