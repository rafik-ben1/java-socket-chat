package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.models.*;
import com.example.projet.models.enums.MessagesType;
import com.example.projet.socketclient.Client;
import com.example.projet.views.ChatListCell;
import com.example.projet.views.MessageListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CurrentChatController implements Initializable, MessageListener {
    @FXML
    public ListView<ChatMessage> messageListView;

    public ObservableList<ChatMessage> messages;
    @FXML
    public TextField messageField;


    @FXML
    public void sendMessage(ActionEvent event) {
        ChatMessage msg = new ChatMessage(Model.getInstance().selectedChatProperty().get().getChatId(),
                messageField.getText(), Client.getInstance().getUser().getUserId());
        msg.setType(MessagesType.CHAT_MESSAGE);
        Client.getInstance().sendMessage(msg);
        messageField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client.getInstance().addChatsListener(this);
        messages = FXCollections.observableArrayList(Model.getInstance().selectedChatProperty().get().getMessages());
        messageListView.setItems(messages);
        messageListView.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>() {
            @Override
            public ListCell<ChatMessage> call(ListView<ChatMessage> messageListView) {
                return new MessageListCell();
            }
        });
        Model.getInstance().selectedChatProperty().addListener((observableValue, chat, t1) -> {
            Platform.runLater(()->{
                if (t1 != null){
                    messages = FXCollections.observableArrayList(t1.getMessages());
                }else {
                    messages.clear();
                }

            });
        });
    }

    @Override
    public void listen(List<Chat> chatList) {
        Platform.runLater(() -> {
            Chat selectedChat = Model.getInstance().selectedChatProperty().get();
                Chat curr = chatList.stream()
                        .filter(chat -> chat.getChatId() == selectedChat.getChatId())
                        .findFirst()
                        .orElse(null);
                if (curr != null) {
                    List<ChatMessage> currMessages = curr.getMessages();
                    messages.setAll(currMessages);
                }

        });
    }
}
