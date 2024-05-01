package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.enums.MessagesType;


public class CreateUser extends Message {

    private String username;
    private String gender;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
     public String getGender(){return gender;}
    public void setGender(String gender){
        this.gender = gender;
    }
    public CreateUser(int clientID) {
        setType(MessagesType.NEW_USER);
        setClientId(clientID);
    }
}
