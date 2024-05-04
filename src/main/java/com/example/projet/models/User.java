package com.example.projet.models;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Objects;


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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return userId == user.userId && Objects.equals(userName, user.userName) && Objects.equals(avatar, user.avatar) && Objects.equals(gender, user.gender) && Objects.equals(avatarColor, user.avatarColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, avatar, gender, avatarColor);
    }

    @Override
    public String toString(){
        return userName;
    }
}
