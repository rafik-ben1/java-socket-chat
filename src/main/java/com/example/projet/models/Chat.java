package com.example.projet.models;

import com.example.projet.models.enums.ChatType;
import com.example.projet.server.MessagingSession;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int chatId;
    private String chatName;

    private ChatType type;
    private List<MessagingSession> participants;
    public Chat(int chatId){

        this.chatId = chatId;
        participants = new ArrayList<>();
    }
    public void sendMessageToParticipants(ChatMessage message){
        participants.forEach(participant -> participant.sendMessage(message) );
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public void addParticipant(MessagingSession messagingSession){
        participants.add(messagingSession);
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
