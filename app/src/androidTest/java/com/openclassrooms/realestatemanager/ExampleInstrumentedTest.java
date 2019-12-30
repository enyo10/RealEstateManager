package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {

   private Context mContext;

    @Before
    public void setup(){
        mContext= InstrumentationRegistry.getInstrumentation().getTargetContext();

    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.

        assertEquals("com.openclassrooms.realestatemanager",mContext.getPackageName());

    }
    @Test
    public void internetAvailabilityTest()throws Exception{

        assertTrue(Utils.isInternetAvailable(mContext));

    }
}
