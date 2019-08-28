package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.UserDao;
import com.openclassrooms.realestatemanager.models.User;

public class UserDataRepository {

    private final UserDao userDao;

    public UserDataRepository(UserDao userDao) { this.userDao = userDao; }

    //-- Insert user to db.
    public void createUser(User user){
        this.userDao.createUser(user);
    }

    // --- GET USER ---
    public LiveData<User> getLoggedInUser(String userId) { return this.userDao.getLoggedInUser(userId); }

}
