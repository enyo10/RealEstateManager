package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.realestatemanager.database.RealEstateDataBase;
import com.openclassrooms.realestatemanager.models.User;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import static junit.framework.TestCase.assertTrue;


public class RealEstateTest {
    // FOR DATA
    private RealEstateDataBase database;

    // DATA SET FOR TEST
    private static String USER_ID = "enyo10@yahoo.fr";
    private static User USER_DEMO = new User(USER_ID, "Roan","Ronny","");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        this.database = Room.inMemoryDatabaseBuilder(
              //  InstrumentationRegistry.getContext()
                ApplicationProvider.getApplicationContext(),
                RealEstateDataBase.class)
                .allowMainThreadQueries()
                .build();
    }


    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.userDao().createUser(USER_DEMO);
        // TEST
        User loggedInUser = LiveDataTestUtil.getValue(this.database.userDao().getLoggedInUser(USER_ID));
        assertTrue(loggedInUser.getFirstName().equals(USER_DEMO.getFirstName()) && loggedInUser.getId().equals(USER_ID));
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
