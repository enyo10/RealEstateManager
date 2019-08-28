package com.openclassrooms.realestatemanager.utils;

import androidx.room.TypeConverter;

import java.sql.Date;


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
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
