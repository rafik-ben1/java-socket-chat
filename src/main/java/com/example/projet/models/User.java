package com.example.projet.models;

import javafx.scene.paint.Color;

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

    private String avatarColor;

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
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
        return this.userName;
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

    @Override
    public String toString(){
        return userName;
    }
}
