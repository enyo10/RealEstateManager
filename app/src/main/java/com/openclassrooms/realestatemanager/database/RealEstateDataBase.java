package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.database.dao.RealEstateDao;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.utils.DateConverter;

@Database(entities = {RealEstate.class, User.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class RealEstateDataBase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile RealEstateDataBase INSTANCE;

    // --- DAO ---
    public abstract RealEstateDao realEstateDao();
    public abstract UserDao userDao();

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
                contentValues.put("id", "enyo10@yahoo.fr");
                contentValues.put("firstName", "Enyo");
                contentValues.put("lastName","Tovissou");


                db.insert("User", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }


}
