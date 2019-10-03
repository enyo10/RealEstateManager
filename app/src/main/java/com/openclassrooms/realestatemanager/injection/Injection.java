package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateDataBase;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    private static final String TAG =Injection.class.getName();
    private static final long USER_ID=1;

   /* public static RealEstateDataRepository provideRealEstateDataSource(Context context) {
        RealEstateDataBase database = RealEstateDataBase.getInstance(context);
        Log.i(TAG, " in injection " + database.userDao().getUser(USER_ID).getValue());
        return new RealEstateDataRepository(database.realEstateDao());
    }

    public static UserDataRepository provideUserDataSource(Context context) {
        RealEstateDataBase database = RealEstateDataBase.getInstance(context);
        return new UserDataRepository(database.userDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RealEstateDataRepository dataSourceRealEstate = provideRealEstateDataSource(context);
        UserDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceRealEstate, dataSourceUser, executor);
    }*/

    public static RealEstateDataRepository provideRealEstateDataSource(Context context) {
        RealEstateDataBase database = RealEstateDataBase.getInstance(context);
        return new RealEstateDataRepository(database.realEstateDao());
    }

    public static UserDataRepository provideUserDataSource(Context context) {
        RealEstateDataBase database = RealEstateDataBase.getInstance(context);
        return new UserDataRepository(database.userDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        RealEstateDataRepository dataSourceRealEstate = provideRealEstateDataSource(context);
        UserDataRepository dataSourceUser = provideUserDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceRealEstate, dataSourceUser, executor);
    }
}