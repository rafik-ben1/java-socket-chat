package com.example.projet;

import com.example.projet.socketclient.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new Thread(Client.getInstance()).start();
      Model.getInstance().getViewFactory().init();
    }

    public static void main(String[] args) {
        launch();
    }
}