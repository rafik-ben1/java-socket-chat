package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.models.User;
import com.example.projet.socketclient.Client;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable  {

    @FXML
    private BorderPane parent;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().selectedOnLeftProperty().addListener((observableValue, s, t1) ->{
            System.out.println("t1  "+ t1);
            if (t1.equals("search")) {
                parent.setLeft(Model.getInstance().getViewFactory().getSearch());
                System.out.println("case search");
            } else if (t1.equals("myChats")) {
                parent.setLeft(Model.getInstance().getViewFactory().getMyChats());
            }
        } );
    }
}
