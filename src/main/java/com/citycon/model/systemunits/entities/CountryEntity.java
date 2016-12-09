package com.citycon.model.systemunits.entities;

import java.io.Serializable;

/**
 * Created by Vojts on 09.12.2016.
 */
public class CountryEntity implements Serializable {
    private String name;
    private int countCities;

    public String getName(){
        return name;
    }
    public int getCountCities() {
        return countCities;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCountCities(int countCities) {
        this.countCities = countCities;
    }
}
