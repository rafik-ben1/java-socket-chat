package com.example.projet.views;

import com.example.projet.models.User;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class UserListCell extends ListCell <User> {
    private final Label avatarLabel;
    private final Label usernameLabel;

    public UserListCell() {
        super();
        avatarLabel = new Label();
        usernameLabel = new Label();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    @Override
    protected void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (empty || user == null) {
            setGraphic(null);
        } else {
            avatarLabel.setText(user.getUserName().toUpperCase().substring(0,1));
            usernameLabel.setText(user.getUserName());
            avatarLabel.setStyle("-fx-background-color: " + user.getAvatarColor()+";"+
                            "-fx-background-radius: 50%; " +
                            "-fx-text-fill: white;"+
                            "-fx-font-weight: bold;"
                    );
            usernameLabel.setStyle("-fx-font-weight: bold;");
            usernameLabel.setAlignment(Pos.CENTER);
            avatarLabel.setPrefHeight(40);
            avatarLabel.setPrefWidth(40);
            avatarLabel.setAlignment(Pos.CENTER);
            HBox hbox = new HBox(avatarLabel, usernameLabel);
            setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: lightgray;");
            hbox.setSpacing(15);
            setGraphic(hbox);
        }
    }
}
