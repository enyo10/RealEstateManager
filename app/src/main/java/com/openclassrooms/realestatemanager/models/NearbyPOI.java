package com.openclassrooms.realestatemanager.models;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({NearbyPOI.SCHOOL,NearbyPOI.SHOPPING_CENTER,NearbyPOI.SPORT_CENTER,NearbyPOI.PARK,
        NearbyPOI.POST_OFFICE,NearbyPOI.TRAIN_STATION,NearbyPOI.HOSPITAL,NearbyPOI.BUS_STATION,
        NearbyPOI.RESTAURANT})
public @ interface NearbyPOI {

    String SCHOOL ="SCHOOL";
    String SHOPPING_CENTER="SHOPPING CENTER";
    String PARK="PARK";
    String HOSPITAL="HOSPITAL";
    String RESTAURANT="RESTAURANT";
    String BUS_STATION="BUS STATION";
    String TRAIN_STATION="TRAIN STATION";
    String SPORT_CENTER="SPORT CENTER";
    String POST_OFFICE="POST OFFICE";

}
