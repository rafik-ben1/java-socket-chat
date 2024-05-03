package com.example.projet.controllers;

import com.example.projet.HelloApplication;
import com.example.projet.Model;
import com.example.projet.dto.CreateUser;
import com.example.projet.socketclient.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    ColorPicker avatatColor;
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
        Color color = avatatColor.getValue();
        String hexColor = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        createUser.setAvatarColor(hexColor);
        createUser.setGender(gender);
        Client.getInstance().sendMessage(createUser);
        Model.getInstance().getViewFactory().showMain();


    }

}
