package com.example.projet.controllers;

import com.example.projet.HelloApplication;
import com.example.projet.Model;
import com.example.projet.dto.SearchUser;
import com.example.projet.socketclient.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectChatController {
    @FXML
    private TextField searchField;

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


}
