package com.example.projet.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class CurrentChatController {
    @FXML
    public ListView messageListView;
    @FXML
    public TextField messageField;
    @FXML
    public void sendMessage(ActionEvent event) {
    System.out.println(messageField.getText());
    }
}
