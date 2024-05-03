package com.example.projet.models;

import com.example.projet.models.enums.ChatType;
import com.example.projet.server.MessagingSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat  {
    private int chatId;
    private String chatName;

    private ChatType type;
    private List<User> participants;
    public Chat(int chatId){

        this.chatId = chatId;
        participants = new ArrayList<>();
    }
//    public void sendMessageToParticipants(Message message){
//        participants.forEach(participant -> participant.sendMessage(message) );
//    }


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

}
