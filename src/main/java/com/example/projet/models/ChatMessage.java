package com.example.projet.models;


import com.example.projet.models.enums.MessagesType;

public class ChatMessage extends Message {
    private int chatId;
    private String content;

    public ChatMessage(int chatId, String content, int clientId) {
        this.chatId = chatId;
        this.content = content;
        setType(MessagesType.CHAT_MESSAGE);
        setClientId(clientId);

    }

    public int getChatId() {
        return chatId;
    }

    public String getContent() {
        return content;
    }


}
