package com.openclassrooms.realestatemanager.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.models.RealEstateImage;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RealImageLIstConverter {

    @TypeConverter
    public static ArrayList<RealEstateImage> fromString(String json) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<RealEstateImage> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


   /* public  String objectToJson(Object object){
        Gson json =new Gson();
        return json.toJson(object);
    }*/

    /**
     *
     * @param json,
     *        A string object to transform to game ArrayList
     * @return ArrayList
     *        The array list of game.
     */
   /* public ArrayList<RealEstateImage> jsonToMoodLinkedList(String json){
        Gson gson=new Gson();

        Type founderListType = new TypeToken<ArrayList<RealEstateImage>>(){}.getType();

        return gson.fromJson(json, founderListType);
    }

*/


}
