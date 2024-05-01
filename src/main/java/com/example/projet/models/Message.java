package com.example.projet.models;

import com.example.projet.models.enums.MessagesType;

import java.io.Serializable;

public class Message implements Serializable {

    private int clientId;
    private MessagesType type;



    public MessagesType getType() {
        return type;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setType(MessagesType type) {
        this.type = type;
    }
}
