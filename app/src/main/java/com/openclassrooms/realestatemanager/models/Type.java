package com.openclassrooms.realestatemanager.models;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({Type.DUPLEX,Type.LOFT,Type.MANOR,Type.PENTHOUSE})
public @interface Type {
    String DUPLEX="DUPLEX";
    String LOFT="LOFT";
    String MANOR="MANOR";
    String PENTHOUSE="PENTHOUSE";



}
