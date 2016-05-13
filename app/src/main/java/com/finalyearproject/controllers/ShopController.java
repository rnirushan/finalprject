package com.finalyearproject.controllers;


import android.content.Context;

import com.finalyearproject.databasehelper.DatabaseHandler;
import com.finalyearproject.dto.Category;
import com.finalyearproject.dto.MeasuredValues;
import com.finalyearproject.dto.Measurement;
import com.finalyearproject.dto.SubCategory;

import java.util.List;

public class ShopController {

    DatabaseHandler databaseHandler;

    public ShopController(Context context){

        databaseHandler = new DatabaseHandler(context);

    }
    /*
    * get all categories from Database*/
    public List<Category> getAllCategories(){

        return databaseHandler.getCategories();

    }
    /*
    * get all sub categories for given main category from database*/
    public List<SubCategory> getSubCategories(int mainCategoryId){

        return databaseHandler.getSubCategories(mainCategoryId);

    }

    /*
    * get recommended measurement for given sub category from database*/
    public List<Measurement> getMeasurements(int subCategoryId){

        return databaseHandler.getMeasurements(subCategoryId);

    }

    public Category getCategoryById(int id){

        return databaseHandler.getCategoryById(id);

    }

    public SubCategory getSubCategoryByName(String name){

        return databaseHandler.getSubCategoryByName(name);

    }

    public MeasuredValues getMeasuredValue (int measurementId){
        return databaseHandler.getMeasuredValue(measurementId);
    }

}
