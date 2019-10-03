package com.openclassrooms.realestatemanager.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.RealEstate;

import java.util.List;

@Dao
public interface RealEstateDao {
  @Query("SELECT * FROM RealEstate WHERE userId = :userId")
  LiveData<List<RealEstate>> getRealEstates(long userId);

  @Insert
  long insertRealEstate(RealEstate realEstate);

  @Update
  int updateRealEstate(RealEstate realEstate);

  @Query("DELETE FROM RealEstate WHERE id = :restateId")
  int deleteRealEstate(long restateId);



  @Query("SELECT * FROM RealEstate WHERE userId = :userId")
  Cursor getRealEstatesWithCursor(long userId);

}
