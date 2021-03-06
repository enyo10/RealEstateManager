package com.openclassrooms.realestatemanager.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.RealEstateDao;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

public class RealEstateDataRepository {
    private static final String TAG=RealEstateDataRepository.class.getName();
    private final RealEstateDao realEstateDao;
    private MutableLiveData<Long>insertResult=new MutableLiveData<>();
    private MutableLiveData<Integer>updateResult=new MutableLiveData<>();

    public RealEstateDataRepository(RealEstateDao realEstateDao) { this.realEstateDao = realEstateDao; }

    // --- GET ---

    public LiveData<List<RealEstate>> getRealEstates(long userId){ return this.realEstateDao.getRealEstates(userId); }

    // --- CREATE ---

    public void createRealEstate(RealEstate realEstate){
      long value =   realEstateDao.insertRealEstate(realEstate);
      Log.i(TAG," insertion Repositories "+value);
      insertResult.postValue(value);
    }

    // --- DELETE ---
    public void deleteRealEstate(long itemId){ realEstateDao.deleteRealEstate(itemId); }

    // --- UPDATE ---
    public void updateRealEstate(RealEstate realEstate){
        int value = realEstateDao.updateRealEstate(realEstate);
        updateResult.postValue(value);
    }


    // The raw search.
    public LiveData<List<RealEstate>> rawQuerySearch(SupportSQLiteQuery query){
        return this.realEstateDao.rawQuerySearch(query);

    }



//-- To keep track on insert result.
    public MutableLiveData<Long> getInsertResult() {
        return insertResult;
    }
    public MutableLiveData<Integer>getUpdateResult(){ return updateResult;
    }
}
