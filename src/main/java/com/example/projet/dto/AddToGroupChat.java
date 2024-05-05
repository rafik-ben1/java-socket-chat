package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.enums.MessagesType;

public class AddToGroupChat extends Message {
    private int chatId;
    private int addedUserId;

    public AddToGroupChat(int chatId, int addedUserId) {
        setType(MessagesType.ADD_TO_CHAT);
        this.chatId = chatId;
        this.addedUserId = addedUserId;
    }

    public int getChatId() {
        return chatId;
    }

    public int getAddedUserId() {
        return addedUserId;
    }
}
