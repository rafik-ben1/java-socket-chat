package com.example.projet.views;

import com.example.projet.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {


    private AnchorPane login;
    private FlowPane MyChats;
    private Stage stage;


    private VBox search;

    public ViewFactory(){
    }


    public Stage getStage(){
        return stage;
    }

    public AnchorPane getLogin() {
        if(login == null){
            try {
                login = new FXMLLoader(HelloApplication.class.getResource("login.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return login;
    }

    public FlowPane getMyChats() {
        if(MyChats == null){
            try {
                MyChats = new FXMLLoader(HelloApplication.class.getResource("MyChats.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return MyChats;
    }
    public VBox getSearch() {
        if(search == null){
            try {
                search = new FXMLLoader(HelloApplication.class.getResource("Search.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return search;
    }
    public void init(){
        stage = new Stage();
        Scene scene = new Scene(getLogin());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    public void showMain(){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MainView.fxml"));
        try {
            BorderPane borderPane = loader.load();
            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}