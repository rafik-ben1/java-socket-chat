package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatType;
import com.example.projet.models.enums.MessagesType;

import java.util.List;

public class CreateChat extends Message {

    private String chatName;




    private int participant;


    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }


    public void setParticipant(int participant) {
        this.participant = participant;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public int getParticipant() {
        return participant;
    }

    private ChatType chatType;


    public ChatType getChatType() {
        return chatType;
    }



    public CreateChat(String chatName  , ChatType type){
        setType(MessagesType.CREATE_CHAT);
        this.chatName = chatName;
        this.chatType = type;
    }

}
