package com.openclassrooms.realestatemanager.utils;

import androidx.databinding.InverseMethod;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DataConverter {

    @InverseMethod("convertIntToString")
    public int convertStringToInt(String value) {

            return Integer.parseInt(value);

    }

    public  String convertIntToString(int value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertDoubleToString")
    public double convertStringToDouble(String value) {
            return Double.parseDouble(value);

    }

    public  String convertDoubleToString(double value) {
        return String.valueOf(value);
    }


    public static String convertPriceToString(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(price);
    }
}
