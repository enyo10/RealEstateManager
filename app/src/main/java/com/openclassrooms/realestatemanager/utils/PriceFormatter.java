package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.models.RealEstate;

public class PriceFormatter {

    private boolean dollarFormat=true;

    public String formatPrice(RealEstate realEstate){

        return null;
    }

    private String fromDollarToEure(RealEstate realEstate){
        return DataConverter.formatPriceToEuroFormat(realEstate.getPrice());
    }

    private String fromEuroToDollar(RealEstate realEstate){
        return DataConverter.formatPriceToDollarFormat(realEstate.getPrice());
    }

    public boolean isDollarFormat() {
        return dollarFormat;
    }

    public void setDollarFormat(boolean dollarFormat) {
        this.dollarFormat = dollarFormat;
    }
}
