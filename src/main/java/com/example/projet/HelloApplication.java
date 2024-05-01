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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("chat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        new Thread(Client.getInstance()).start();
    }

    public static void main(String[] args) {
        launch();
    }
}