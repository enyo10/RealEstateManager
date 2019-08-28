package com.openclassrooms.realestatemanager.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.authentication.SignUpViewModel;
import com.openclassrooms.realestatemanager.authentication.UserViewModel;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;


public class ViewModelFactory implements ViewModelProvider.Factory {


        private final UserDataRepository userDataSource;
        private final Executor executor;

        public ViewModelFactory( UserDataRepository userDataSource, Executor executor) {

            this.userDataSource = userDataSource;
            this.executor = executor;
        }

        /*@Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return null;
            if (modelClass.isAssignableFrom(UserViewModel.class)) {
                return (T) new UserViewModel(userDataSource, executor);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }*/


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel( userDataSource, executor);
        }
        if(modelClass.isAssignableFrom(SignUpViewModel.class)){
            return (T)new SignUpViewModel(userDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
