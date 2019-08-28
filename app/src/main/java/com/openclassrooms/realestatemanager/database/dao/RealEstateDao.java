package com.openclassrooms.realestatemanager.database.dao;

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
  LiveData<List<RealEstate>> getItems(long userId);

  @Insert
  long insertItem(RealEstate realEstate);

  @Update
  int updateItem(RealEstate realEstate);

  @Query("DELETE FROM RealEstate WHERE id = :restateId")
  int deleteItem(long restateId);

}
