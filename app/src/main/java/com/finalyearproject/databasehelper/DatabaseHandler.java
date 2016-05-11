package com.finalyearproject.databasehelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalyearproject.dto.Category;
import com.finalyearproject.dto.Measurement;
import com.finalyearproject.dto.SubCategory;
import com.finalyearproject.dto.User;
import com.finalyearproject.util.DBDataGenerator;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TapeIT";

    private static final String TABLE_USERS = "users";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_SUBCATEGORIES = "subcategories";
    private static final String TABLE_MEASUREMENTS = "measurements";

    private static final String USER_KEY_ID = "id";
    private static final String USER_KEY_NAME = "name";
    private static final String USER_KEY_EMAIL = "email";
    private static final String USER_KEY_PASSWORD = "password";

    private static final String CATEGORY_KEY_ID = "id";
    private static final String CATEGORY_KEY_NAME = "categoryname";
    private static final String CATEGORY_KEY_ICON_PATH = "iconpath";

    private static final String SUBCATEGORY_KEY_ID = "id";
    private static final String SUBCATEGORY_KEY_CATEGORY_ID = "categoryid";
    private static final String SUBCATEGORY_KEY_TITLE = "title";
    private static final String SUBCATEGORY_KEY_DESCRIPTION = "description";

    private static final String MEASUREMENT_KEY_ID = "id";
    private static final String MEASUREMENT_KEY_SUBCATEGORY_ID = "subcategoryid";
    private static final String MEASUREMENT_KEY_TITLE = "title";
    private static final String MEASUREMENT_KEY_VALUE = "value";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_NAME
                + " TEXT," + USER_KEY_EMAIL + " TEXT," + USER_KEY_PASSWORD + " TEXT"
                + ")";

        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + "("
                + CATEGORY_KEY_ID + " INTEGER PRIMARY KEY," + CATEGORY_KEY_NAME
                + " TEXT," + CATEGORY_KEY_ICON_PATH + " TEXT"
                + ")";

        String CREATE_SUBCATEGORIES_TABLE = "CREATE TABLE " + TABLE_SUBCATEGORIES + "("
                + SUBCATEGORY_KEY_ID + " INTEGER PRIMARY KEY," + SUBCATEGORY_KEY_CATEGORY_ID
                + " INTEGER," + SUBCATEGORY_KEY_TITLE + " TEXT," +
                SUBCATEGORY_KEY_DESCRIPTION + " TEXT" + ")";

        String CREATE_MEASUREMENTS_TABLE = "CREATE TABLE " + TABLE_MEASUREMENTS + "("
                + MEASUREMENT_KEY_ID + " INTEGER PRIMARY KEY," + MEASUREMENT_KEY_SUBCATEGORY_ID
                + " INTEGER," + MEASUREMENT_KEY_TITLE + " TEXT," +
                MEASUREMENT_KEY_VALUE + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_SUBCATEGORIES_TABLE);
        db.execSQL(CREATE_MEASUREMENTS_TABLE);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_NAME, user.getName());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public User getUserDto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { USER_KEY_ID,
                        USER_KEY_NAME, USER_KEY_EMAIL, USER_KEY_PASSWORD}, USER_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(1), cursor.getString(2),
                cursor.getString(3));

        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));

                user.setId(Integer.parseInt(cursor.getString(0)));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_NAME, user.getName());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_PASSWORD, user.getPassword());

        // updating row
        return db.update(TABLE_USERS, values, USER_KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getCategoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getSubCategoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SUBCATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getMeasurmentCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MEASUREMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void addCategory(Category category){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CATEGORY_KEY_NAME, category.getName());
        values.put(CATEGORY_KEY_ICON_PATH, category.getIconPath());

        // Inserting Row
        db.insert(TABLE_CATEGORIES, null, values);
        db.close(); // Closing database connection
    }

    public Category getCategoryByName(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] { CATEGORY_KEY_ID,
                        CATEGORY_KEY_NAME, CATEGORY_KEY_ICON_PATH},
                CATEGORY_KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(cursor.getString(1), cursor.getString(2));
        category.setId(Integer.parseInt(cursor.getString(0)));

        return category;


    }

    public Category getCategoryById(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES, new String[] { CATEGORY_KEY_ID,
                        CATEGORY_KEY_NAME, CATEGORY_KEY_ICON_PATH},
                CATEGORY_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Category category = new Category(cursor.getString(1), cursor.getString(2));
        category.setId(Integer.parseInt(cursor.getString(0)));

        return category;


    }

    public List<Category> getCategories(){

        List<Category> categoryList = new ArrayList<Category>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category( cursor.getString(1),
                        cursor.getString(2));

                category.setId(Integer.parseInt(cursor.getString(0)));

                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        return categoryList;
    }

    public void addSubCategories(SubCategory subCategory){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBCATEGORY_KEY_CATEGORY_ID, subCategory.getCategoryId());
        values.put(SUBCATEGORY_KEY_TITLE, subCategory.getTitle());
        values.put(SUBCATEGORY_KEY_DESCRIPTION, subCategory.getDescription());

        // Inserting Row
        db.insert(TABLE_SUBCATEGORIES, null, values);
        db.close(); // Closing database connection
    }

    public SubCategory getSubCategoryByName(String name){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBCATEGORIES, new String[] { SUBCATEGORY_KEY_ID,
                        SUBCATEGORY_KEY_CATEGORY_ID, SUBCATEGORY_KEY_TITLE,
                        SUBCATEGORY_KEY_DESCRIPTION},
                SUBCATEGORY_KEY_TITLE + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        SubCategory subCategory = new SubCategory(Integer.parseInt(cursor.getString(1)),
                cursor.getString(2),cursor.getString(3));
        subCategory.setId(Integer.parseInt(cursor.getString(0)));

        return subCategory;


    }

    public List<SubCategory> getSubCategories (int mainCategoryId){

        List<SubCategory> subCategoryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBCATEGORIES, new String[] { SUBCATEGORY_KEY_ID,
                SUBCATEGORY_KEY_CATEGORY_ID, SUBCATEGORY_KEY_TITLE,
                SUBCATEGORY_KEY_DESCRIPTION}, SUBCATEGORY_KEY_CATEGORY_ID + "=?",
                new String[] { String.valueOf(mainCategoryId) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                SubCategory subCategory = new SubCategory( Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2), cursor.getString(3));

                subCategory.setId(Integer.parseInt(cursor.getString(0)));

                subCategoryList.add(subCategory);
            } while (cursor.moveToNext());
        }

        return subCategoryList;
    }

    public void addMeasurement(Measurement measurement){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_KEY_SUBCATEGORY_ID, measurement.getSubcategoryId());
        values.put(MEASUREMENT_KEY_TITLE, measurement.getTitle());
        values.put(MEASUREMENT_KEY_VALUE, measurement.getValue());

        // Inserting Row
        db.insert(TABLE_MEASUREMENTS, null, values);
        db.close(); // Closing database connection

    }

    public List<Measurement> getMeasurements(int subCategoryId){

        List<Measurement> measurementList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEASUREMENTS, new String[] { MEASUREMENT_KEY_ID,
                        MEASUREMENT_KEY_SUBCATEGORY_ID, MEASUREMENT_KEY_TITLE,
                        MEASUREMENT_KEY_VALUE}, MEASUREMENT_KEY_SUBCATEGORY_ID + "=?",
                new String[] { String.valueOf(subCategoryId) }, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Measurement measurement = new Measurement( Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2), cursor.getString(3));

                measurement.setId(Integer.parseInt(cursor.getString(0)));

                measurementList.add(measurement);
            } while (cursor.moveToNext());
        }

        return measurementList;

    }

    public int updateMeasurement(Measurement measurement){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_KEY_VALUE, measurement.getValue());

        // updating row
        return db.update(TABLE_MEASUREMENTS, values, MEASUREMENT_KEY_ID + " = ?",
                new String[] { String.valueOf(measurement.getId()) });

    }



}
