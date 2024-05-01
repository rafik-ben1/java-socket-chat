package com.example.projet.models;

import java.io.Serializable;


public class User implements Serializable {
    private  int userId;
    private String userName;
    private static final long serialVersionUID = -1279381259221382181L;

    public User(int userId)
    {
             this.userId = userId;
    }
    public User(){}

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private String avatar;

    private String gender;







    public int getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getAvatar() {
        return avatar;
    }


}
