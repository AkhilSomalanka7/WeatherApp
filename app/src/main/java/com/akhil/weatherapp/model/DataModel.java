package com.akhil.weatherapp.model;

public class DataModel {
    String attribute;
    String value;

    public DataModel(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

}
