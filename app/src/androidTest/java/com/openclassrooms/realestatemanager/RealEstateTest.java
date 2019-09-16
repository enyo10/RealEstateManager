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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


public class RealEstateTest {
    // FOR DATA
    private RealEstateDataBase database;

    // DATA SET FOR TEST
    private static long USER_ID = 1;
    private static User USER_DEMO = new User(USER_ID, "Roan","Ronny","");
    private  static Address address=new Address();
    private static ArrayList<String>photoUrl=new ArrayList<>();

    private static RealEstate NEW_PENTHOUSE = new RealEstate(USER_ID, Type.PENTHOUSE, 270000,180, 6, "Nice Penthouse to sell",photoUrl,address);

    private static RealEstate NEW_DUPLEX = new RealEstate(USER_ID, Type.DUPLEX, 410000,204, 7, "Nice DUPLEX to sell",photoUrl,address);



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
        User user = LiveDataTestUtil.getValue(this.database.userDao().getUser(USER_ID));
        assertTrue(user.getFirstName().equals(USER_DEMO.getFirstName()) && user.getId()==(USER_ID));
    }

    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {
        // TEST
        List<RealEstate> realEstates = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertTrue(realEstates.isEmpty());
    }

    @Test
    public void insertAndGetItems() throws InterruptedException {
        // BEFORE : Adding demo user & demo RealEstate

        this.database.userDao().createUser(USER_DEMO);
        this.database.realEstateDao().insertRealEstate(NEW_DUPLEX);
        this.database.realEstateDao().insertRealEstate(NEW_PENTHOUSE);


        // TEST
        List<RealEstate> items = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
        assertEquals(2, items.size());
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo RealEstate. Next, update RealEstate added & re-save it
        this.database.userDao().createUser(USER_DEMO);
        this.database.realEstateDao().insertRealEstate(NEW_PENTHOUSE);
        RealEstate realEstateAdded = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID)).get(0);
      //  realEstateAdded.setSelected(true);

        this.database.realEstateDao().updateRealEstate(realEstateAdded);

        //TEST
        List<RealEstate> realEstates = LiveDataTestUtil.getValue(this.database.realEstateDao().getRealEstates(USER_ID));
       // assertTrue(realEstates.size() == 1 && realEstates.get(0).getSelected());
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
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
