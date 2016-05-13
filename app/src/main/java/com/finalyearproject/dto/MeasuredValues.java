package com.finalyearproject.dto;


public class MeasuredValues {

    private int id;

    private int measurementId;

    private int userId;

    private String value;

    public MeasuredValues(int id, int measurementId,int userId,String value){
        this.id = id;
        this.measurementId = measurementId;
        this.userId = userId;
        this.value = value;
    }

    public MeasuredValues(int measurementId,int userId,String value){
        this.measurementId = measurementId;
        this.userId = userId;
        this.value = value;
    }

    public MeasuredValues(int measurementId,int userId){
        this.measurementId = measurementId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(int measurementId) {
        this.measurementId = measurementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
