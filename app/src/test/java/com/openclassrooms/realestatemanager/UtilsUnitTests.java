package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsUnitTests {

    @Test
    public void convertDollarToEuro() {
        int dollar =100;
        int euro = Utils.convertDollarToEuro(dollar);
        assertEquals(81, euro);

    }

    @Test
    public void convertEuroToDollar() {
        assertEquals(100,Utils.convertEuroToDollar(81));
    }

    @Test
    public void getTodayDate() {
        assertEquals("2019/08/18",Utils.getTodayDate());
    }

    @Test
    public void getTodayDateFormat() {
        assertEquals("18/08/2019",Utils.getTodayDateFormat());
    }

    @Test
    public void isInternetAvailable() {
    }
}
