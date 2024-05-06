package com.example.projet.models;


import com.example.projet.models.enums.ChatMessageType;
import com.example.projet.models.enums.MessagesType;

public class ChatMessage extends Message {
    private int chatId;
    private String content;
    private ChatMessageType messageType;

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    public ChatMessage(int chatId, String content, int clientId) {
        this.chatId = chatId;
        this.content = content;
        setType(MessagesType.CHAT_MESSAGE);
        setMessageType(ChatMessageType.REGULAR);
        setClientId(clientId);

    }

    public ChatMessageType getMessageType() {
        return messageType;
    }

    public int getChatId() {
        return chatId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString(){return content;}

}
