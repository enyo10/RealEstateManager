package com.openclassrooms.realestatemanager.utils;

import android.text.TextUtils;

import androidx.databinding.InverseMethod;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class DataConverter {

    @InverseMethod("convertIntToString")
    public int convertStringToInt(String value) {
        int a=0;
        if(!TextUtils.isEmpty(value))
            a=Integer.parseInt(value);
        return a;
    }


    public  String convertIntToString(int value) {
        return String.valueOf(value);

    }


    @InverseMethod("convertDoubleToString")
    public double convertStringToDouble(String value) {

        double d=0;
        if(!TextUtils.isEmpty(value))
            d= Double.parseDouble(value);
        return d;
    }

    public  String convertDoubleToString(double value) {
        return String.valueOf(value);

    }


    public String convertLongToString(Long value){
        return Long.toString(value);

    }


    @InverseMethod("convertLongToString")
    public Long convertStringToLong(String value){

        return Long.valueOf(value);
    }


    public static String convertPriceToString(double price) {

        DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(price);

    }

    public static String formatPriceToDollarFormat(double value){

        String pattern ="$###,###.###";

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(pattern);

        return df.format(value);

    }

    public static String formatPriceToEuroFormat(double value){
        String pattern ="###.###,###€";
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern(pattern);

        return df.format(value);

    }


    @InverseMethod("formatPriceToDollarFormat")

    public static double dollarFormatToDouble(String value){
        String newValue=value.replace("$","").replaceAll(",","");

        return Double.parseDouble(newValue);

    }


}

