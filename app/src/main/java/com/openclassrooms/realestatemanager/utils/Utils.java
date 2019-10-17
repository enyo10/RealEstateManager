package com.openclassrooms.realestatemanager.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.models.RealEstateImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {
    private static final String TAG=Utils.class.getName();
    public static final String apiUri = "https://maps.googleapis.com/maps/api/staticmap?size=300x300&scale=2&zoom=16&markers=size:mid%7Ccolor:blue%7C";
    public static final String apiKey = "&key=";
    //public static final String keyValue = Resources.getSystem().getString(R.string.google_maps_key);

    
    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars,
     * the dollars value.
     * @return the dollars value to return.
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Convertion d'un prix d'un bien immobilier (Euro vers Dollars).
     * @param euro, the value in euro to convert.
     * @return dollar, the dollars value to output.
     */
    public static int convertEuroToDollar(int euro){
        return (int) Math.round(euro/0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.FRENCH);
        return dateFormat.format(new Date());
    }

    public static String getTodayDateFormat(){
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context, the context
     * @return true or false.
     */
    public static Boolean isInternetAvailable(Context context){
        /*WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();*/
        // Instead of WifiManager only we check both wifi and mobile connectivity with connectivity manager
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork!=null;

    }







    public static  boolean isIntegerrValue(String value){
        try
        {
            Integer.parseInt( value );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }


    }

    public static boolean isDoubleValue(String value){
        try
        {
            Double.parseDouble(  value);
            return true;
        }
        catch( Exception e)
        {
            return false;
        }
    }



    /**
     * To convert a string map to string.
     * @param stringMap,
     *        the map of string to convert.
     * @return string,
     *        the string to return.
     */
    public static String mapToJsonString(Map<String,String> stringMap){

        JSONObject jsonObject =new JSONObject(stringMap);
        return jsonObject.toString();
    }

    /**
     * Helper method to convert a json string to a Map<String,String> Object.
     * @param jsonString,
     *        The Json string to convert.
     * @return Map<String,String> , the result of the conversion.
     * @throws JSONException, an exception when the mapping fail.
     */
    public static Map<String, String>jsonToMap(String jsonString)throws JSONException {
        HashMap<String, String> map = new HashMap<>();
        JSONObject jObject = new JSONObject(jsonString);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);

        }
        return map;
    }



    /**
     *
     * @param object,
     *       the object to transform.
     * @return String,
     *        the string value to return.
     *
     */
    public static  String objectToJson(Object object){
        Gson json =new Gson();
        return json.toJson(object);
    }

    public static String arrayListToJson(ArrayList<RealEstateImage>list){
      //  Object o= (Object)list;
        Gson json= new Gson();
        return json.toJson(list);
    }

    /**
     * This static method to transform a json String to an Real Estate Image list.
     * @param json, the string parameter.
     * @return , the return ArrayList
     */
    public static ArrayList<RealEstateImage> jsonStringToRealEstateImageList(String json){
        Gson gson = new Gson();
        Type founderListType = new TypeToken<ArrayList<RealEstateImage>>(){}.getType();

        return gson.fromJson(json, founderListType);

    }

    public static int getRandomNumber(int min,int max){

        int myRandom= min + (int)(Math.random() * ((max - min) + 1));
        return String.valueOf(myRandom).hashCode();

    }


    public static double calculateInterestRate(double amount,double t, int numberOfMount){
        return (amount*(t/12))/(1-Math.pow((1+t/12),-numberOfMount));

    }

    public static void main (String []args){
        System.out.println(calculateInterestRate(70000,0.06,12));
    }



public static boolean connectivtiy(Context context){
    boolean connected ;
    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    //we are connected to a network


    connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    return connected;

}

    public static int getActiveNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return activeNetwork.getType();
        } else {
            return -1;
        }
    }
}







