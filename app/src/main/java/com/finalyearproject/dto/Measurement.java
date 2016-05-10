package com.finalyearproject.dto;

public class Measurement {

    private int id;

    private int subcategoryId;

    private String title;

    private String value;

    public Measurement(int subcategoryId, String title, String value){

        this.subcategoryId = subcategoryId;
        this.title = title;
        this.value = value;

    }

    public Measurement(int subcategoryId, String title){

        this.subcategoryId = subcategoryId;
        this.title =title;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(int subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
