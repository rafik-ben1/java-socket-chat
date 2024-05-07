package com.example.projet.views;

import com.example.projet.Model;
import com.example.projet.models.Chat;
import com.example.projet.models.ChatMessage;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatMessageType;
import com.example.projet.socketclient.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.util.Base64;

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
                contentLabel.setStyle( "-fx-text-fill: #f6f6f6;");
                avatarLabel.setDisable(true);

            }
            avatarLabel.setPrefHeight(35);
            avatarLabel.setPrefWidth(35);
            avatarLabel.setMinHeight(35);
            avatarLabel.setMinWidth(35);
            avatarLabel.setAlignment(Pos.CENTER);
            contentLabel.setAlignment(Pos.CENTER);
            contentLabel.setWrapText(true);
            HBox hbox = new HBox();



            if (message.getClientId() != Client.getInstance().getUser().getUserId()){
                hbox.setStyle("-fx-background-color: lightgray;" +
                        "-fx-background-radius:15px;");
                hbox.setSpacing(15);
                hbox.setTranslateX(0);
            }else {

                hbox.setStyle("-fx-background-color: #229ED1;" +"-fx-background-radius:15px;");
                hbox.setTranslateX(100);
            }

            hbox.setMaxWidth(310);
            hbox.setPadding(new Insets(1));
            hbox.getChildren().addAll(avatarLabel,contentLabel);
            if (message.getMessageType() == ChatMessageType.AUDIO) {
                Button button = new Button("Play audio");
                button.setMinWidth(100);
                button.setStyle("-fx-background-radius:15px;");
                button.setOnAction(e -> {
                    byte[] audioBytes = Base64.getDecoder().decode(message.getContent());
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(audioBytes)) {
                        // Create an audio input stream from the byte array
                        AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
                        AudioInputStream audioInputStream = new AudioInputStream(bis, format, audioBytes.length / format.getFrameSize());

                        // Get the format of the audio input stream
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                        line.open(format);
                        line.start();

                        // Create a buffer to read from the audio input stream
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        // Read from the audio input stream and write to the line
                        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                            line.write(buffer, 0, bytesRead);
                        }

                        // Close the line when done
                        line.drain();
                        line.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                });
                hbox.getChildren().clear();
                hbox.getChildren().addAll(avatarLabel, button);
            }
            if(message.getMessageType() == ChatMessageType.IMAGE){
                byte[] imageBytes = Base64.getDecoder().decode(message.getContent());
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                Image image = new Image(bis);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(300);
                imageView.setPreserveRatio(true);
                hbox.getChildren().clear();
                if (message.getClientId() != Client.getInstance().getUser().getUserId()){
                    hbox.getChildren().add(avatarLabel);
                }
                hbox.getChildren().add(imageView);
                hbox.setStyle("-fx-background-color: white;");
                hbox.setSpacing(15);
            }
            setGraphic(hbox);
        }
    }
}
