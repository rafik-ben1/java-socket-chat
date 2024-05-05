package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.dto.AddToGroupChat;
import com.example.projet.dto.CreateChat;
import com.example.projet.models.Chat;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatType;
import com.example.projet.socketclient.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {
    @FXML
    public Label username;
    @FXML
    public Label bio;
    @FXML
    public Label gender;
    @FXML
    public Label avatar;
    public Button groupChatButton;
    public ChoiceBox<Chat> myGroupChats;

    public ObservableList<Chat> chatObservableList;
    public Chat selectedChat;

    @FXML
    public void addUser(ActionEvent event) {
        User user = Client.getInstance().getUser();
        User userToAdd = Model.getInstance().selectedProfileProperty().get();
        CreateChat createChat = new CreateChat(user.getUserName()+ "/"+userToAdd.getUserName(),
                ChatType.PRIVATE);
        createChat.setClientId(user.getUserId());
        createChat.setParticipant(userToAdd.getUserId());
        Client.getInstance().sendMessage(createChat);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupChatButton.setDisable(true);
        username.setText(Model.getInstance().selectedProfileProperty().get().getUserName());
        gender.setText(Model.getInstance().selectedProfileProperty().get().getGender());
        avatar.setText(Model.getInstance().selectedProfileProperty().get().getUserName().toUpperCase().substring(0,1));
        avatar.setStyle(" -fx-background-radius: 50%;"
        + "-fx-background-color: "+Model.getInstance().selectedProfileProperty().get().getAvatarColor()+";"+
                "-fx-text-fill: white;"
        );
        chatObservableList = FXCollections.observableArrayList(Client.getInstance().getMyChatsProperty().get()
                .stream().filter(chat -> chat.getChatType() == ChatType.GROUP).toList());
        myGroupChats.setItems(chatObservableList);
        myGroupChats.setOnAction(e-> {
            Chat selected = myGroupChats.getValue();
            if(selected != null){
                groupChatButton.setDisable(false);
                selectedChat = selected;
                            }
        });
        Model.getInstance().selectedProfileProperty().addListener((observableValue, user, t1) -> {
            username.setText(t1.getUserName());
            gender.setText(t1.getGender());
            avatar.setText(Model.getInstance().selectedProfileProperty().get().getUserName().toUpperCase().substring(0,1));
            avatar.setStyle(" -fx-background-radius: 50%;"
                            + "-fx-background-color: "+Model.getInstance().selectedProfileProperty().get().getAvatarColor()+";"+
                    "-fx-text-fill: white;"
            );
        });
    }

    public void addToGroupChat(ActionEvent event) {
        AddToGroupChat add = new AddToGroupChat(selectedChat.getChatId(),Model.getInstance().selectedProfileProperty().get().getUserId());
        Client.getInstance().sendMessage(add);
    }
}
