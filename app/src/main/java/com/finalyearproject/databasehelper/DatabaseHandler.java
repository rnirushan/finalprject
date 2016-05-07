package com.finalyearproject.databasehelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.finalyearproject.dto.UserDto;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "TapeIT";

    private static final String TABLE_USERS = "users";

    private static final String USER_KEY_ID = "id";
    private static final String USER_KEY_NAME = "name";
    private static final String USER_KEY_EMAIL = "email";
    private static final String USER_KEY_PASSWORD = "password";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_KEY_ID + " INTEGER PRIMARY KEY," + USER_KEY_NAME
                + " TEXT," + USER_KEY_EMAIL + " TEXT," + USER_KEY_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
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

    void addUser(UserDto user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_NAME, user.getName());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    UserDto getUserDto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { USER_KEY_ID,
                        USER_KEY_NAME, USER_KEY_EMAIL, USER_KEY_PASSWORD}, USER_KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserDto user = new UserDto(cursor.getString(1), cursor.getString(2),
                cursor.getString(3));

        return user;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> userList = new ArrayList<UserDto>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDto user = new UserDto(cursor.getString(1), cursor.getString(2),
                        cursor.getString(3));

                user.setId(Integer.parseInt(cursor.getString(0)));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;
    }

    public int updateUser(UserDto user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_KEY_NAME, user.getName());
        values.put(USER_KEY_EMAIL, user.getEmail());
        values.put(USER_KEY_PASSWORD, user.getPassword());
        
        // updating row
        return db.update(TABLE_USERS, values, USER_KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    public void deleteUser(UserDto user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, USER_KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
