package com.openclassrooms.realestatemanager.authentication;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.repositories.UserDataRepository;

import java.util.concurrent.Executor;

public class UserViewModel extends ViewModel {
    // REPOSITORIES
    private UserDataRepository mUserDataSource;
    private final Executor mExecutor;

    // DATA
    @Nullable
    private LiveData<User> currentUser;

    public UserViewModel(UserDataRepository userDataSource, Executor executor) {

        this.mUserDataSource = userDataSource;
        this.mExecutor = executor;
    }

    public void init(Long userId) {
        if (this.currentUser != null) {
            return;
        }
        currentUser = mUserDataSource.getUser(userId);
    }

    // -------------
    // FOR USER
    // -------------

    /**
     * Get the user,
     * @param userId, the user key to uee to retrieve the user.
     * @return
     *         LiveData<User>,
     */
  //  public LiveData<User> getUser(long userId) { return this.currentUser;  }

    public LiveData<User>getUser(Long userId){
        return mUserDataSource.getUser(userId);
    }


    /**
     * This method to insert a new user to the data base.
     * @param user,
     *         the user to insert.
     */
    /*public void createUser(User user) {
        mExecutor.execute(() -> {
            mUserDataSource.createUser(user);
        });
    }*/




}
