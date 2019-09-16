package com.openclassrooms.realestatemanager.authentication;


import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;

public class SignUpViewModel extends ViewModel {

    // REPOSITORIES
    private UserDataRepository mUserDataSource;
    private final Executor mExecutor;


    public SignUpViewModel(UserDataRepository userDataSource, Executor executor) {

        this.mUserDataSource = userDataSource;
        this.mExecutor = executor;
    }



    // -------------
    // FOR USER
    // -------------


    /**
     * This method to insert a new user to the data base.
     * @param user,
     *         the user to insert.
     */
    public void createUser(User user) {
      /*  mExecutor.execute(() -> {
            mUserDataSource.createUser(user);
        });*/
    }

}
