package com.example.projet.views;

import com.example.projet.Model;
import com.example.projet.models.Chat;
import com.example.projet.models.ChatMessage;
import com.example.projet.models.User;
import com.example.projet.socketclient.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class MessageListCell extends ListCell<ChatMessage> {
    private final Label avatarLabel;
    private final Label contentLabel;

    public MessageListCell() {
        super();
        avatarLabel = new Label();
        contentLabel = new Label();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
    @Override
    protected void updateItem(ChatMessage message, boolean empty) {
        super.updateItem(message, empty);
        if (empty || message == null) {
            setGraphic(null);
        } else {
            User user = Model.getInstance().selectedChatProperty().get().getParticipants().stream()
                    .filter(u -> u.getUserId() == message.getClientId() )
                    .toList().get(0);
            avatarLabel.setText(user.getUserName().toUpperCase().substring(0,1));
            contentLabel.setText(message.getContent());
            if (message.getClientId() != Client.getInstance().getUser().getUserId()){
                avatarLabel.setStyle("-fx-background-color: " + user.getAvatarColor()+";"+
                        "-fx-background-radius: 50%; " +
                        "-fx-text-fill: white;"+
                        "-fx-font-weight: bold;"
                );
                contentLabel.setStyle( "-fx-text-fill: black;");

            }else {
                avatarLabel.setDisable(true);
                avatarLabel.setStyle("-fx-background-color:#229ED1 ;"+
                        "-fx-background-radius: 50%; " +
                        "-fx-text-fill: #229ED1;"+
                        "-fx-font-weight: bold;"
                );
                avatarLabel.setMaxWidth(1);
                contentLabel.setStyle( "-fx-text-fill: #f6f6f6;");
            }
            avatarLabel.setPrefHeight(35);
            avatarLabel.setPrefWidth(35);
            avatarLabel.setAlignment(Pos.CENTER);
            contentLabel.setAlignment(Pos.CENTER);
            contentLabel.setWrapText(true);
            HBox hbox = new HBox(avatarLabel, contentLabel);



            if (message.getClientId() != Client.getInstance().getUser().getUserId()){
                hbox.setStyle("-fx-background-color: lightgray;" +
                        "-fx-background-radius:15px;");
                hbox.setSpacing(15);
                hbox.setTranslateX(0);
            }else {

                hbox.setStyle("-fx-background-color: #229ED1;" +"-fx-background-radius:15px;");
                hbox.setTranslateX(100);
            }
            hbox.setMaxWidth(300);
            hbox.setPadding(new Insets(1));

            setGraphic(hbox);
        }
    }
}
