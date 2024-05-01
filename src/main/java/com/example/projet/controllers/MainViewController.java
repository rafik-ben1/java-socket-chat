package com.example.projet.controllers;

import com.example.projet.models.User;
import com.example.projet.socketclient.Client;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
     private Label username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            username.setText(Client.getInstance().getUser().getUserName());
        });
    }
}
