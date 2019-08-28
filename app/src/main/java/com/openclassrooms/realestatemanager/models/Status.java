package com.openclassrooms.realestatemanager.models;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({Status.SOLD,Status.UNSOLD})
@Retention(RetentionPolicy.SOURCE)
@ interface  Status{

     String SOLD  ="SOLD";
     String UNSOLD="UNSOLD";


}
