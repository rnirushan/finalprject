package com.finalyearproject.util;


import android.content.Context;

import com.finalyearproject.databasehelper.DatabaseHandler;
import com.finalyearproject.dto.Category;
import com.finalyearproject.dto.Measurement;
import com.finalyearproject.dto.SubCategory;

import java.util.ArrayList;
import java.util.List;

public class DBDataGenerator {

    DatabaseHandler databaseHandler;

    DBDataGenerator(Context context){
        databaseHandler = new DatabaseHandler(context);
    }

    public void generateData(){

    }

    public void generateCategories(){

        //creating category objects

        Category tops = new Category("Tops","tops");
        Category tShirts = new Category("T-Shirts","tshirts");
        Category dress = new Category("Dress","dress");
        Category pants = new Category("Pants","pants");
        Category shorts = new Category("Shorts","shorts");
        Category skirts = new Category("Skirts","skirts");
        Category jackets = new Category("Jackets","jackets");
        Category swaeter = new Category("Swaeter","swaeter");
        Category shoes = new Category("Shoes","shoes");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(tops);
        categoryList.add(tShirts);
        categoryList.add(dress);
        categoryList.add(pants);
        categoryList.add(shorts);
        categoryList.add(skirts);
        categoryList.add(jackets);
        categoryList.add(swaeter);
        categoryList.add(shoes);

        //database insertion

        for(Category category:categoryList){
            databaseHandler.addCategory(category);
        }

    }

    public void generateSubCategories(){

        //creating subcategories

        SubCategory peplum = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Peplum","Peplum tops are an elegant structure that was originated in Greece. " +
                "Their main measurements are based on the waist, and their ratios are mainly" +
                " derived from how big the waist is as the elastic is mainly focused over that " +
                "area. Therefore it is best recommended when shopping for peplum tops " +
                "to measure the waist. ");

        SubCategory openCollar = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Open Collar","");

        SubCategory deepVNeck = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Deep V Neck","");

        SubCategory deepCircleNeck = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Deep Circle Neck","");

        SubCategory turtleNeck = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Turtle Neck","");

        SubCategory collaredNeck = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Collared Neck","");

        SubCategory egyptianSideShoulder = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Egyptian Side Shoulder","");

        SubCategory openShoulder = new SubCategory(databaseHandler.getCategoryByName("Tops").getId(),
                "Open Shoulder","");

        SubCategory vNeck = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "V Neck","");

        SubCategory circleNeck = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "Circle Neck","");

        SubCategory longSleeve = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "Long Sleeve","");

        SubCategory shortSleeve = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "Short Sleeve","");

        SubCategory sleeveless = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "Sleeveless","");

        SubCategory tankTops  = new SubCategory(databaseHandler.getCategoryByName("T-Shirts").getId(),
                "Tank Tops","");

        SubCategory skinnyPants = new SubCategory(databaseHandler.getCategoryByName("Pants").getId(),
                "Skinny Pants","The skinny pants became famous in the 2005 onwards were fashion went" +
                " from loose fitted to tightly fitted. The skinny pants are usually made using a" +
                " ratio with the leg length and the waist. Some jeans also measure that ankle but " +
                "in rare cases. Therefore to shop for these type it is recommended " +
                "to measure the leg length and the waist");

        SubCategory denimPants = new SubCategory(databaseHandler.getCategoryByName("Pants").getId(),
                "Denim Pants","");

        SubCategory highWaist = new SubCategory(databaseHandler.getCategoryByName("Pants").getId(),
                "High Waist","");

        SubCategory wideLeg = new SubCategory(databaseHandler.getCategoryByName("Pants").getId(),
                "Wide Leg","");

        SubCategory lowWaist = new SubCategory(databaseHandler.getCategoryByName("Pants").getId(),
                "Low Waist","");


        //database insertion

        databaseHandler.addSubCategories(peplum);
        databaseHandler.addSubCategories(openCollar);
        databaseHandler.addSubCategories(deepVNeck);
        databaseHandler.addSubCategories(deepCircleNeck);
        databaseHandler.addSubCategories(turtleNeck);
        databaseHandler.addSubCategories(collaredNeck);
        databaseHandler.addSubCategories(egyptianSideShoulder);
        databaseHandler.addSubCategories(openShoulder);

        databaseHandler.addSubCategories(vNeck);
        databaseHandler.addSubCategories(circleNeck);
        databaseHandler.addSubCategories(longSleeve);
        databaseHandler.addSubCategories(shortSleeve);
        databaseHandler.addSubCategories(sleeveless);
        databaseHandler.addSubCategories(tankTops);

        databaseHandler.addSubCategories(skinnyPants);
        databaseHandler.addSubCategories(denimPants);
        databaseHandler.addSubCategories(highWaist);
        databaseHandler.addSubCategories(wideLeg);
        databaseHandler.addSubCategories(lowWaist);

    }

    public void generateMeasurements(){

        //create recommended measurements

        Measurement peplumWaist = new Measurement(databaseHandler.getSubCategoryByName("Peplum").getId(),
                "WAIST");

        Measurement skinnyPantsWaist = new Measurement(databaseHandler.getSubCategoryByName("Skinny Pants").getId(),
                "WAIST");

        Measurement skinnyPantLegLength = new Measurement(databaseHandler.getSubCategoryByName("Skinny Pants").getId(),
                "LEG LENGTH ");

        //database insertion

        databaseHandler.addMeasurement(peplumWaist);
        databaseHandler.addMeasurement(skinnyPantsWaist);
        databaseHandler.addMeasurement(skinnyPantLegLength);


    }

}
