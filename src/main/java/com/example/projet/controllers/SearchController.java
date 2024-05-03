package com.example.projet.controllers;

import com.example.projet.Model;

import com.example.projet.models.User;

import com.example.projet.socketclient.Client;
import com.example.projet.views.UserListCell;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import javafx.scene.layout.BorderPane;
import javafx.util.Callback;


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
        users.setStyle("-fx-selection-bar: #229ED1;");
        if (Client.getInstance().searchedUsersProperty().get() == null){
            userObservableList = FXCollections.observableList(List.of());
        }else {userObservableList = FXCollections
                .observableList(Client.getInstance().searchedUsersProperty().get());}
        users.setItems(userObservableList);
        users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        users.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> listView) {
                return new UserListCell();
            }
        });
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
