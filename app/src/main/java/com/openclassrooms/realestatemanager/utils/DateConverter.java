package com.openclassrooms.realestatemanager.utils;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateConverter {

       /* @TypeConverter
        public static Date toDate(long dateLong) {
            return new Date(dateLong);
        }

        @TypeConverter
        public static long fromDate(Date date) {
            return date.getTime();
        }
*/
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String formatDate( Date date){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(date.getTime());
    }

}
