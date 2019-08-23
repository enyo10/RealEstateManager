package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.models.Administrator;
import com.openclassrooms.realestatemanager.models.RealEstate;
@Dao
public interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createAdministrator(Administrator administrator);

    @Query("SELECT * FROM Administrator WHERE id = :userId")
    LiveData<Administrator> getAdmin(long userId);
}
