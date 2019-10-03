package com.openclassrooms.realestatemanager.utils;

import androidx.databinding.InverseMethod;

public class DataConverter {

    @InverseMethod("convertIntToString")
    public int convertStringToInt(String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public  String convertIntToString(int value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertDoubleToString")
    public double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public  String convertDoubleToString(double value) {
        return String.valueOf(value);
    }
}
