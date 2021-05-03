package com.example.fitbook.users;

import java.util.List;

public class Users {
    private String DisplayName;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Image_Url;
    private String User_UID;
    private Boolean IsNightMode;
    private Float TotalDistanceTravelled;

    public Users() {
    }

    public Users(String displayName, String firstName, String lastName, String email, String image_Url, String user_UID, Boolean isNightMode, Float totalDistanceTravelled) {
        DisplayName = displayName;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Image_Url = image_Url;
        User_UID = user_UID;
        IsNightMode = isNightMode;
        TotalDistanceTravelled = totalDistanceTravelled;
    }


    //Getters//
    public String getDisplayName() {
        return DisplayName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public String getUser_UID() {
        return User_UID;
    }

    public Boolean getNightMode() {
        return IsNightMode;
    }

    public Float getTotalDistanceTravelled() {
        return TotalDistanceTravelled;
    }

    //Setters//
    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }

    public void setUser_UID(String user_UID) {
        User_UID = user_UID;
    }

    public void setNightMode(Boolean nightMode) {
        IsNightMode = nightMode;
    }

    public void setTotalDistanceTravelled(Float totalDistanceTravelled) {
        TotalDistanceTravelled = totalDistanceTravelled;
    }

}
