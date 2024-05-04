package com.example.projet.views;

import com.example.projet.HelloApplication;
import com.example.projet.controllers.MyChatsController;
import com.example.projet.socketclient.Client;
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
    private VBox MyChats;
    private Stage stage;

    private VBox chat;
    private VBox search;
    private VBox userProfile;

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

//    public void showMyChats() {
//        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("MyChats.fxml"));
//        VBox pane;
//        try {
//            pane = loader.load();
//            MyChatsController controller = loader.getController();
//            Client.getInstance().SetChatsListener(controller);
//            BorderPane root = (BorderPane) stage.getScene().getRoot();
//            root.setLeft(pane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public VBox getMyChats() {
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
    public VBox getChat() {
        if(chat == null){
            try {
                chat = new FXMLLoader(HelloApplication.class.getResource("Chat.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return chat;
    }
    public VBox getUserProfile() {
        if(userProfile == null){
            try {
                userProfile = new FXMLLoader(HelloApplication.class.getResource("UserProfile.fxml")).load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userProfile;
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
