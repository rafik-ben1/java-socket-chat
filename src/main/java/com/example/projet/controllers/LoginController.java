package com.example.projet.controllers;

import com.example.projet.HelloApplication;
import com.example.projet.dto.CreateUser;
import com.example.projet.socketclient.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    RadioButton male, female;
    @FXML
    TextField userName;

    String gender;

    @FXML
    void setGender(ActionEvent event){
        if(male.isSelected()){
            gender = "male";
        } else {
            gender = "female";
        }
    }
    @FXML
    void login(ActionEvent event) throws IOException {
        Client client = Client.getInstance();
        CreateUser createUser = new CreateUser(client.getUser().getUserId());
        createUser.setUsername(userName.getText());
        createUser.setGender(gender);
        Client.getInstance().sendMessage(createUser);
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MainView.fxml"));

        VBox box = loader.load();
        Scene scene = new Scene(box);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
