package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

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
    public void getTodayDateFormatTest() {

        Date date = new Date();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month=month+1;
        int dateOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        String format =dateOfMonth+"/"+month+"/"+year;



        assertEquals(format,Utils.getTodayDateFormat());
    }


}
