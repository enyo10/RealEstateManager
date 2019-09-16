package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.RealEstateDao;
import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

public class RealEstateDataRepository {
    private final RealEstateDao realEstateDao;

    public RealEstateDataRepository(RealEstateDao realEstateDao) { this.realEstateDao = realEstateDao; }

    // --- GET ---

    public LiveData<List<RealEstate>> getRealEstates(long userId){ return this.realEstateDao.getRealEstates(userId); }

    // --- CREATE ---

    public void createRealEstate(RealEstate realEstate){ realEstateDao.insertRealEstate(realEstate); }

    // --- DELETE ---
    public void deleteRealEstate(long itemId){ realEstateDao.deleteRealEstate(itemId); }

    // --- UPDATE ---
    public void updateRealEstate(RealEstate realEstate){ realEstateDao.updateRealEstate(realEstate); }
}
