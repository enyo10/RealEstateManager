package com.openclassrooms.realestatemanager.management.views;


import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class RealEstateViewModel extends ViewModel {
    private static final String TAG = RealEstateViewModel.class.getName();

    public MutableLiveData<Double> price = new MutableLiveData<>();
    public MutableLiveData<Integer> numberOfPieces = new MutableLiveData<>();
    public MutableLiveData<Double> area = new MutableLiveData<>();
    public MutableLiveData<String>description=new MutableLiveData<>();
    public MutableLiveData<String> country = new MutableLiveData<>();
    public MutableLiveData<String> city = new MutableLiveData<>();
    public MutableLiveData<Integer> zip = new MutableLiveData<>();
    public MutableLiveData<String> street = new MutableLiveData<>();
    public MutableLiveData<String> streetNumber = new MutableLiveData<>();

    private MutableLiveData<RealEstate> mEstateMutableLiveData ;

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



    public void onRealEstateSave(View view) {
        Date dateOfEntry =new Date();
        Date dateOfSale=null;

        Log.d(TAG,"Number of pieces "+ numberOfPieces.getValue());
        Log.d(TAG,"Area  "+ area.getValue());
        Log.d(TAG," Description "+ description.getValue());
        Log.d(TAG," Country : "+country.getValue());
        Log.d(TAG, " City " +city.getValue());
        Log.d(TAG," Zip :"+zip.getValue());
        Log.d(TAG," Street :"+street.getValue());
        Log.d(TAG," Street number :"+streetNumber.getValue());
        Log.d(TAG,"Price "+price.getValue());



        Address address= new Address(country.getValue(),1,city.getValue(),street.getValue(),streetNumber.getValue());

       /* RealEstate realEstate = new  RealEstate("userId", Type.HOUSE,price.getValue(), area.getValue(),
                numberOfPieces.getValue()  , "Description", new ArrayList<>(), address,
        Status.UNSOLD, dateOfEntry, dateOfSale);
*/

        //mEstateMutableLiveData.setValue(realEstate);


    }

















}
