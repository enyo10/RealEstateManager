package com.openclassrooms.realestatemanager.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.authentication.SignUpViewModel;
import com.openclassrooms.realestatemanager.authentication.UserViewModel;
import com.openclassrooms.realestatemanager.management.views.RealEstateViewModel;
import com.openclassrooms.realestatemanager.repositories.RealEstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private static final String TAG=ViewModelFactory.class.getName();

        private final RealEstateDataRepository realEstateDataSource;
        private final UserDataRepository userDataSource;
        private final Executor executor;


    public ViewModelFactory(RealEstateDataRepository realEstateDataSource, UserDataRepository userDataSource, Executor executor) {
        this.realEstateDataSource = realEstateDataSource;
        this.userDataSource = userDataSource;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RealEstateViewModel.class)) {
            return (T) new RealEstateViewModel(realEstateDataSource, userDataSource, executor);
        }
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel( userDataSource, executor);
        }
        if(modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T)new SignUpViewModel(userDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
