package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context mContext;

    @Before
    public void setup(){
        mContext=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getContext();

    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.openclassrooms.realestatemanager", appContext.getPackageName());

       // assertEquals("com.openclassrooms.realestatemanager", mContext.getPackageName());
    }
    @Test
    public void internetAvailabilityTest()throws Exception{

        assertTrue(Utils.isInternetAvailable(mContext));



    }
}
