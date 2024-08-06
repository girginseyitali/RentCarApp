package com.seyitaligirgin.rentcarapp.model;

public class Car {

    public String brandName; //BMW
    public String modelName; //320
    public String modelYear; //2019
    public String brandLogoUrl;


    public Car(String brandName, String modelName, String modelYear, String brandLogoUrl) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.brandLogoUrl = brandLogoUrl;
    }
}
