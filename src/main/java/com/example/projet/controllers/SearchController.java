package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.models.User;
import com.example.projet.models.listeners.SearchListener;
import com.example.projet.socketclient.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

     @FXML
     private ListView<User> users;
    @FXML
    public void goBack(){
        Scene scene = Model.getInstance().getViewFactory().getStage().getScene();
        BorderPane pane = (BorderPane) scene.getRoot();
        pane.setLeft(Model.getInstance().getViewFactory().getMyChats());
     }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client.getInstance().searchedUsersProperty().addListener((observableValue, users1, t1) -> {
            System.out.println(t1.get(0).getGender());
        });
    }
}
