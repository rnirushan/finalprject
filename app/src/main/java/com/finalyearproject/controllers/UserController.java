package com.finalyearproject.controllers;


import android.content.Context;

import com.finalyearproject.databasehelper.DatabaseHandler;
import com.finalyearproject.dto.User;

import java.util.List;

public class UserController {

    DatabaseHandler databaseHandler;

    public UserController(Context context){
        databaseHandler = new DatabaseHandler(context);
    }

    public boolean registerUser(User user){
        databaseHandler.addUser(user);
        return  true;
    }

    public boolean validateUser(User user){
        List<User> userList = databaseHandler.getAllUsers();
        for(User userFromDB:userList){
            if(user.equals(userFromDB)){
                DatabaseHandler.loggedinUserId = userFromDB.getId();
                return true;
            }
        }
        return  false;
    }

    public boolean isEmailDuplicated(String email){
        return databaseHandler.isEmailDuplicated(email);
    }

}
