package com.example.projet.models;

import com.example.projet.models.enums.ChatType;
import com.example.projet.server.MessagingSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chat implements Serializable {
    private int chatId;
    private String chatName;

    private ChatType type;
    private List<User> participants;
    public Chat(int chatId){

        this.chatId = chatId;
        messages = new ArrayList<>();
        participants = new ArrayList<>();
    }
    private List<ChatMessage> messages;

    public void addMessage(ChatMessage chatMessage){
        messages.add(chatMessage);
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public void addParticipant(User user){
        participants.add(user);
    }


    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatName() {
        return chatName;
    }
    public ChatType getChatType(){return type;}

    @Override
    public String toString() {
        return chatName;
    }
}
