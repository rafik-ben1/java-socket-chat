package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.dto.SearchUser;
import com.example.projet.models.Chat;
import com.example.projet.socketclient.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MyChatsController implements Initializable {
    @FXML
    private TextField searchField;

    @FXML
    private Label avatar;
    @FXML
    private ListView<Chat> chatsListView;

    @FXML
    public void search(ActionEvent event){
        //Sending request for searching users
        SearchUser searchUser = new SearchUser();
        searchUser.setClientId(Client.getInstance().getUser().getUserId());
        searchUser.setSearchingFor(searchField.getText());
        Client.getInstance().sendMessage(searchUser);

        ///Switching scene to the search one
        Scene scene = Model.getInstance().getViewFactory().getStage().getScene();
        BorderPane pane = (BorderPane) scene.getRoot();
        pane.setLeft(Model.getInstance().getViewFactory().getSearch());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            avatar.setText(Client.getInstance().getUser().getUserName().toUpperCase().substring(0,1));
        } );
    }
}
