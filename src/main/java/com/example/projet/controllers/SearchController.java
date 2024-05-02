package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.dto.CreateChat;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatType;
import com.example.projet.models.listeners.SearchListener;
import com.example.projet.socketclient.Client;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

     @FXML
     private ListView<User> users;

    ObservableList<User> userObservableList;
    @FXML
    public void goBack(){
        Scene scene = Model.getInstance().getViewFactory().getStage().getScene();
        BorderPane pane = (BorderPane) scene.getRoot();
        pane.setLeft(Model.getInstance().getViewFactory().getMyChats());
     }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userObservableList = FXCollections
                .observableList(Client.getInstance().searchedUsersProperty().get());
        users.setItems(userObservableList);
        users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        users.getSelectionModel().selectedItemProperty().addListener(userSelectionListener);
        Client.getInstance().searchedUsersProperty().addListener((observableValue, users1, t1) -> {
            Platform.runLater(()->{
                t1.forEach(u -> System.out.println(u.getUserName()));
                 userObservableList =  FXCollections.observableList(t1);
                users.setItems(userObservableList);
            });

        });
    }

    private final ChangeListener<User> userSelectionListener = new ChangeListener<User>() {
        @Override
        public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
            handleUserSelection(newValue);
        }
    };
    private void handleUserSelection(User selectedUser) {
        if (selectedUser != null) {
            Model.getInstance().selectedProfileProperty().set(selectedUser);
            Scene scene = Model.getInstance()
                    .getViewFactory().getStage().getScene();
            BorderPane pane = (BorderPane) scene.getRoot();
            pane.setCenter(Model.getInstance().getViewFactory().getUserProfile());
        }
    }
}
