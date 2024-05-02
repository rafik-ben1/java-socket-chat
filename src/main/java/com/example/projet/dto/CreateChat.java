package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.enums.ChatType;
import com.example.projet.models.enums.MessagesType;

public class CreateChat extends Message {

    private String chatName;

    private int createdBy;

    @Override
    public String toString() {
        return "CreateChat{" +
                "chatName='" + chatName + '\'' +
                ", createdBy=" + createdBy +
                ", participant=" + participant +
                ", chatType=" + chatType +
                '}';
    }

    private int participant;

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public CreateChat(String chatName , int createdBy , ChatType type){
        setType(MessagesType.CREATE_CHAT);
        this.chatName = chatName;
        this.createdBy = createdBy;
        this.chatType = type;
    }

}
