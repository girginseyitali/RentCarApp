package com.seyitaligirgin.rentcarapp.model;

import androidx.versionedparcelable.ParcelField;

import java.util.ArrayList;


public class Car {

    public String brandName; //BMW
    public String modelName; //320
    public String modelYear; //2019
    public String brandLogoUrl;
    public String[] modelImages;


    public Car(String brandName, String modelName, String modelYear, String brandLogoUrl, String[] modelImages) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.brandLogoUrl = brandLogoUrl;
        this.modelImages = modelImages;
    }
}
