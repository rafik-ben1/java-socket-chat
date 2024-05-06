package com.example.projet.controllers;

import com.example.projet.Model;

import com.example.projet.models.*;
import com.example.projet.models.enums.ChatMessageType;
import com.example.projet.models.enums.MessagesType;
import com.example.projet.socketclient.Client;
import com.example.projet.views.MessageListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class CurrentChatController implements Initializable, MessageListener {
    @FXML
    public ListView<ChatMessage> messageListView;

    public ObservableList<ChatMessage> messages;
    @FXML
    public TextField messageField;
    @FXML
    ImageView audioHandler;
    Image startRecording = new Image("rcrd.png");

    Image stopRecording = new Image("stop.png");

    private TargetDataLine line;
    private Thread captureThread;
    private ByteArrayOutputStream byteArrayOutputStream;

    @FXML
    public void sendMessage(ActionEvent event) {
        ChatMessage msg = new ChatMessage(Model.getInstance().selectedChatProperty().get().getChatId(),
                messageField.getText(), Client.getInstance().getUser().getUserId());
        msg.setType(MessagesType.CHAT_MESSAGE);
        Client.getInstance().sendMessage(msg);
        messageField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        audioHandler.setImage(startRecording);
         audioHandler.setOnMouseClicked(this::handleAudio);
        Client.getInstance().addChatsListener(this);
        messages = FXCollections.observableArrayList(Model.getInstance().selectedChatProperty().get().getMessages());
        messageListView.setItems(messages);
        messageListView.setStyle("-fx-selection-bar: white;");
        messageListView.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>() {
            @Override
            public ListCell<ChatMessage> call(ListView<ChatMessage> messageListView) {
                return new MessageListCell();
            }
        });
        Model.getInstance().selectedChatProperty().addListener((observableValue, chat, t1) -> {
            Platform.runLater(()->{
                if (t1 != null){
                    messages = FXCollections.observableArrayList(t1.getMessages());
                }else {
                    messages.clear();
                }

            });
        });
    }
    public void handleAudio(MouseEvent event) {
        if (audioHandler.getImage().equals(startRecording)) {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                line = (TargetDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                audioHandler.setImage(stopRecording);
                captureThread = new Thread(() -> {
                    byte[] buffer = new byte[4096];
                    while (audioHandler.getImage().equals(stopRecording) && !Thread.currentThread().isInterrupted()) {
                        int bytesRead = line.read(buffer, 0, buffer.length);
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                });
                captureThread.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } else {
            if (line != null && line.isOpen()) {
                try {
                    // Stop capturing audio
                    audioHandler.setImage(startRecording);
                    line.stop();
                    line.close();

                    // Interrupt the capture thread to stop it
                    captureThread.interrupt();

                    // Join the thread after it has been interrupted
                    captureThread.join();

                    // Convert audio to Base64
                    byte[] audioBytes = byteArrayOutputStream.toByteArray();
                    String base64Audio = Base64.getEncoder().encodeToString(audioBytes);
                    ChatMessage msg = new ChatMessage(Model.getInstance().selectedChatProperty().get().getChatId(),
                            base64Audio, Client.getInstance().getUser().getUserId());
                    msg.setMessageType(ChatMessageType.AUDIO);
                    Client.getInstance().sendMessage(msg);
                    System.out.println("Audio: " + base64Audio);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
     @FXML
     public void triggerFileSelect(MouseEvent event){
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Select an image to send");
          File chosen =fileChooser.showOpenDialog(Model.getInstance().getViewFactory().getStage());
           if (chosen != null){
               try {
               byte[] imageContent = Files.readAllBytes(chosen.toPath());

                   String base64Image = Base64.getEncoder().encodeToString(imageContent);
                   ChatMessage imageMessage = new ChatMessage(Model.getInstance().selectedChatProperty().get().getChatId(),
                           base64Image,Client.getInstance().getUser().getUserId());
                   imageMessage.setMessageType(ChatMessageType.IMAGE);
                   Client.getInstance().sendMessage(imageMessage);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
    }

    @Override
    public void listen(List<Chat> chatList) {
        Platform.runLater(() -> {
            Chat selectedChat = Model.getInstance().selectedChatProperty().get();
            Chat curr = chatList.stream()
                    .filter(chat -> chat.getChatId() == selectedChat.getChatId())
                    .findFirst()
                    .orElse(null);
            if (curr != null) {
                List<ChatMessage> currMessages = curr.getMessages();
                messages.setAll(currMessages);
                messageListView.scrollTo(messages.size()-1);
            }

        });
    }

    }


