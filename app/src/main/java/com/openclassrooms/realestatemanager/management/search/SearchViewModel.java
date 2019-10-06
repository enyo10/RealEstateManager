package com.openclassrooms.realestatemanager.management.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class SearchViewModel extends ViewModel {

    private RealEstateDataRepository realEstateDataSource;
    private Executor mExecutor;

    public MutableLiveData<Integer>minRoom=new MutableLiveData<>();
    public MutableLiveData<Integer>maxRoom=new MutableLiveData<>();
    public MutableLiveData<Integer>minSurface=new MutableLiveData<>();
    public MutableLiveData<Integer>maxSurface=new MutableLiveData<>();
    public MutableLiveData<Double>minPrice=new MutableLiveData<>();
    public MutableLiveData<Double>maxPrice=new MutableLiveData<>();
    public MutableLiveData<String>area=new MutableLiveData<>();
    public MutableLiveData<String>typeOfReal=new MutableLiveData<>();



    public SearchViewModel(RealEstateDataRepository realEstateDataSource, Executor executor) {
        this.realEstateDataSource = realEstateDataSource;
        mExecutor = executor;
    }

    // -------------
    // FOR ITEM
    // -------------

    public LiveData<List<RealEstate>> getRealEstates(long userId) {
        return realEstateDataSource.getRealEstates(userId);
    }

}
