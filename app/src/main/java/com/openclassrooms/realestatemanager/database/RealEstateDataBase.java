package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.AdminDao;
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao;
import com.openclassrooms.realestatemanager.models.Administrator;
import com.openclassrooms.realestatemanager.models.RealEstate;

@Database(entities = {RealEstate.class, Administrator.class}, version = 1, exportSchema = false)
public abstract class RealEstateDataBase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile RealEstateDataBase INSTANCE;

    // --- DAO ---
    public abstract RealEstateDao realEstateDao();
    public abstract AdminDao adminDao();

    // --- INSTANCE ---
    public static RealEstateDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDataBase.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Philippe");


                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }


}
