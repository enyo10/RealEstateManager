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

  @Query("SELECT * FROM RealEstate WHERE userId = :userId AND type LIKE :type AND surface LIKE :area AND surface BETWEEN :minSurface AND :maxSurface" +
          " AND price BETWEEN :minPrice AND :maxPrice AND numberOfRooms BETWEEN :minRoom AND :maxRoom ")
  LiveData<List<RealEstate>> searchRealEstate(String type, String area, Integer minSurface, Integer maxSurface, Long minPrice, Long maxPrice,
                                              Integer minRoom, Integer maxRoom, long userId);


}
