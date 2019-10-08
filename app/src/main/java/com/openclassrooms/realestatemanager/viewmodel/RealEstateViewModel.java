package com.openclassrooms.realestatemanager.viewmodel;


import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Address;
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
    public final MutableLiveData<Double>price =new MutableLiveData<>();



    //  FOR SEARCH DATA.
    public MutableLiveData<Integer>minRoom=new MutableLiveData<>();
    public MutableLiveData<Integer>maxRoom=new MutableLiveData<>();
    public MutableLiveData<Integer>minSurface=new MutableLiveData<>();
    public MutableLiveData<Integer>maxSurface=new MutableLiveData<>();
    public MutableLiveData<Double>minPrice=new MutableLiveData<>();
    public MutableLiveData<Double>maxPrice=new MutableLiveData<>();
    public MutableLiveData<String>area=new MutableLiveData<>();
    public MutableLiveData<String>typeOfReal=new MutableLiveData<>();

    // REPOSITORIES
    private final RealEstateDataRepository realEstateDataSource;
    private final UserDataRepository userDataSource;
    private final Executor executor;
    private final MutableLiveData<RealEstate> selectedRealEstate = new MutableLiveData<>();

    private MutableLiveData<RealEstate> mEstateMutableLiveData;
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

    public void setSelectedRealEstate(RealEstate realEstate) {
        this.selectedRealEstate.setValue(realEstate);
    }



    /**
     * this method to save the RealEstate object in the data base.
     * , the view clicked to fire the action save.
     */
    public void onRealEstateSave() {

       // String aType=type.getValue();
        Integer nbrOfRooms = numberOfRooms.getValue();
        Integer nbrOfBathRoom = numberOfBathRooms.getValue();
        Integer nbrOfBedRoom = numberOfBedRooms.getValue();
        Double aSurface = surface.getValue();
        String desc = description.getValue();
        String myCountry = country.getValue();
        String cty = city.getValue();
        String zp = zip.getValue();
        String str = street.getValue();
        String st_nbr = streetNumber.getValue();
        Double aPrice = price.getValue();



        Address address = new Address(myCountry, zp, cty, str, st_nbr);

        RealEstate realEstate = new RealEstate(1, type, aPrice, aSurface,nbrOfRooms,nbrOfBedRoom, nbrOfBathRoom, desc,getSetRealEstateImagesString(), address,nearbyValues);
        realEstate.setDateOfEntry(new Date());
        realEstate.setImages(getSetRealEstateImagesString());

        Log.i(TAG, "Real estate created");
        Log.i(TAG, " price   ----- "+price);

        createRealEstate(realEstate);

    }

    public void onSearch(){

    }


}

