package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.openclassrooms.realestatemanager.database.RealEstateDataBase;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.RealEstate;
import com.openclassrooms.realestatemanager.models.Type;
import com.openclassrooms.realestatemanager.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class RealEstateTest {
    // FOR DATA
    private RealEstateDataBase database;

    // DATA SET FOR TEST
    private static long USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Roan","Ronny","");
    private  static Address address=new Address();
    private static double price =2900000;
    private static double area=97;
    private static int numberOfPieces=5;
    private static int numberOfBathrooms=2;
    private static int numberOfBedRooms=3;
    private static String description="jolie maison à vendre";
    private static String imageList="";
    private  static ArrayList<String>poiList=new ArrayList<>();

    private static RealEstate NEW_PENTHOUSE = new RealEstate(USER_ID, Type.PENTHOUSE,price, area, numberOfPieces,numberOfBathrooms, numberOfBedRooms, description,imageList,address,poiList);

    private static RealEstate NEW_DUPLEX =new RealEstate(USER_ID, Type.DUPLEX,price, area, numberOfPieces,numberOfBathrooms, numberOfBedRooms, description,imageList,address,poiList);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {

        this.database = Room.inMemoryDatabaseBuilder(
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
        User user = LiveDataTestUtil.getValue(this.database.userDao().getUser(USER_ID));
        assertTrue(user.getFirstName().equals(USER_DEMO.getFirstName()) && user.getId()==(USER_ID));
    }

    @Test
    public void getRealEstatesListWhenNoRealEstateInserted() throws InterruptedException {
        // TEST
        List<RealEstate> realEstates = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertTrue(realEstates.isEmpty());
    }

    @Test
    public void insertAndGetRealEstatesListSize() throws InterruptedException {
        // BEFORE : Adding demo user & demo RealEstate

        this.database.userDao().createUser(USER_DEMO);
        this.database.realEstateDao().insertRealEstate(NEW_DUPLEX);
        this.database.realEstateDao().insertRealEstate(NEW_PENTHOUSE);


        // TEST
        List<RealEstate> items = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertEquals(2, items.size());
    }

    @Test
    public void insertAndUpdateRealEstate() throws InterruptedException {
        // BEFORE : Adding demo user & demo RealEstate. Next, update RealEstate added & re-save it
        this.database.userDao().createUser(USER_DEMO);
        this.database.realEstateDao().insertRealEstate(NEW_PENTHOUSE);
        RealEstate realEstateAdded = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID)).get(0);
        realEstateAdded.setSold(true);

        this.database.realEstateDao().updateRealEstate(realEstateAdded);

        //TEST
        List<RealEstate> realEstates = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertTrue(realEstates.size() == 1 && realEstates.get(0).isSold());
    }

    @Test
    public void insertAndDeleteRealEstate() throws InterruptedException {
        // BEFORE : Adding demo user & demo RealEstate. Next, get the RealEstate added & delete it.
        this.database.userDao().createUser(USER_DEMO);
         this.database.realEstateDao().insertRealEstate(NEW_DUPLEX);
         RealEstate realEstateAdded = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID)).get(0);
        this.database.realEstateDao().deleteRealEstate(realEstateAdded.getId());

        //TEST
        List<RealEstate> realEstates = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertTrue(realEstates.isEmpty());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }


}
