package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.RealEstateDataBase;
import com.openclassrooms.realestatemanager.models.Administrator;
import org.junit.Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import static junit.framework.TestCase.assertTrue;


public class RealEstateTest {
    // FOR DATA
    private RealEstateDataBase database;

    // DATA SET FOR TEST
    private static long USER_ID = 1;
    private static Administrator ADMIN_DEMO = new Administrator(USER_ID, "Roan","Ronny","");

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
        this.database.adminDao().createAdministrator(ADMIN_DEMO);
        // TEST
        Administrator administrator = LiveDataTestUtil.getValue(this.database.adminDao().getAdmin(USER_ID));
        assertTrue(administrator.getFirstName().equals(ADMIN_DEMO.getFirstName()) && administrator.getId() == USER_ID);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}
