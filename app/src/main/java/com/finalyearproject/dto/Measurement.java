package com.finalyearproject.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Measurement implements Parcelable {

    private int id;

    private int subcategoryId;

    private String title;

    public Measurement(int subcategoryId, String title){

        this.subcategoryId = subcategoryId;
        this.title =title;

    }

    public Measurement(Parcel in){
        this.id = in.readInt();
        this.subcategoryId = in.readInt();
        this.title = in.readString();
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


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Measurement createFromParcel(Parcel in) {
            return new Measurement(in);
        }

        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }

        public ArrayList<Measurement> newArrayList(){
            return new ArrayList<Measurement>();
        }
    };


    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(subcategoryId);
        dest.writeString(title);

    }
}
