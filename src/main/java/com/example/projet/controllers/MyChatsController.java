package com.example.projet.controllers;

import com.example.projet.Model;
import com.example.projet.dto.AddToGroupChat;
import com.example.projet.dto.CreateChat;
import com.example.projet.dto.SearchUser;
import com.example.projet.models.Chat;
import com.example.projet.models.MessageListener;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatType;
import com.example.projet.socketclient.Client;
import com.example.projet.views.ChatListCell;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyChatsController implements Initializable, MessageListener {
    @FXML
    private TextField searchField;

    @FXML
    private Label avatar;
    @FXML
    private ListView<Chat> chatsListView;

     ObservableList<Chat> chatObservableList;

    @FXML
    public void search(ActionEvent event){
        //Sending request for searching users
        SearchUser searchUser = new SearchUser();
        searchUser.setClientId(Client.getInstance().getUser().getUserId());
        searchUser.setSearchingFor(searchField.getText());
        Client.getInstance().sendMessage(searchUser);

        ///Switching scene to the search one
        Scene scene = Model.getInstance().getViewFactory().getStage().getScene();
        BorderPane pane = (BorderPane) scene.getRoot();
        pane.setLeft(Model.getInstance().getViewFactory().getSearch());
    }
    @FXML
    public void showPrompt(ActionEvent e){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create a new group chat");
        dialog.setHeaderText("Enter group chat name");
        dialog.setContentText("chat name: ");
        Optional<String> chatName = dialog.showAndWait();
        chatName.ifPresent(name ->{
            CreateChat create = new CreateChat(name, ChatType.GROUP);
            create.setClientId(Client.getInstance().getUser().getUserId());
            Client.getInstance().sendMessage(create);
        } );
    }


      public void listen(List<Chat> chat){
            System.out.println("listening " + chat + "  messages " + chat.get(0).getMessages());
            chatObservableList.setAll(chat);

       }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client.getInstance().addChatsListener(this);
        chatObservableList = FXCollections.observableArrayList(Client.getInstance().getMyChatsProperty().get());
        chatsListView.setItems(chatObservableList);
        chatsListView.setStyle("-fx-selection-bar: #229ED1;");
        chatsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        chatsListView.setCellFactory(new Callback<ListView<Chat>, ListCell<Chat>>() {
            @Override
            public ListCell<Chat> call(ListView<Chat> chatListView) {
                return new ChatListCell();
            }
        });
        chatsListView.getSelectionModel().selectedItemProperty().addListener(chatSelectionListener);
            Platform.runLater(() -> {
                // Set user avatar
                avatar.setText(Client.getInstance().getUser().getUserName().toUpperCase().substring(0,1));
                avatar.setStyle("-fx-background-color: "+Client.getInstance().getUser().getAvatarColor()+";"+
                        "-fx-background-radius: 50%; " +
                        "-fx-text-fill: white;");
            });



    }

    private final ChangeListener<Chat> chatSelectionListener = new ChangeListener<Chat>() {
        @Override
        public void changed(ObservableValue<? extends Chat> observable, Chat oldValue, Chat newValue) {
            handleChatSelection(newValue);
        }
    };
    private void handleChatSelection(Chat selectedChat) {
        if (selectedChat != null) {
            Model.getInstance().selectedChatProperty().set(selectedChat);
            Scene scene = Model.getInstance()
                    .getViewFactory().getStage().getScene();
            BorderPane pane = (BorderPane) scene.getRoot();
            pane.setCenter(Model.getInstance().getViewFactory().getChat());
        }
    }
}
