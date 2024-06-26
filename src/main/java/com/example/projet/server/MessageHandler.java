package com.example.projet.server;

import com.example.projet.dto.AddToGroupChat;
import com.example.projet.dto.CreateChat;
import com.example.projet.dto.CreateUser;
import com.example.projet.dto.SearchUser;
import com.example.projet.models.Chat;
import com.example.projet.models.ChatMessage;
import com.example.projet.models.User;
import com.example.projet.models.enums.ChatMessageType;
import com.example.projet.models.enums.ChatType;
import com.example.projet.socketclient.Client;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


public class MessageHandler {
    private static List<MessagingSession> clients = new ArrayList<>();
    private static List<Chat> chats = new ArrayList<>();
    private static int nextUserId = 1;
    private static int nextChatId = 1;
    private synchronized int getNextUserId(){
        return nextUserId;
    }
    private synchronized int getNextChatId(){
        return nextChatId;
    }
    private synchronized void increamentNextUserId(){
        nextUserId++;
    }
    private synchronized void increamentNextChatId(){
        nextChatId++;
    }

    public MessageHandler(){}
    public void addClient(MessagingSession client){
        client.setUser(new User(getNextUserId()));
        increamentNextUserId();
        clients.add(client);
        client.sendMessage(client.getUser().getUserId());
    }

    public void createUser(CreateUser createUser){
        MessagingSession session = clients
                .stream()
                .filter(client -> client.getUser().getUserId() == createUser.getClientId())
                .toList().get(0);
        User user = session.getUser();
        user.setUserName(createUser.getUsername());
        user.setGender(createUser.getGender());
        user.setAvatarColor(createUser.getAvatarColor());
        session.setUser(user);
        System.out.println(user.getUserName());
        session.sendMessage(user);

    }
    public void searchUser(SearchUser searchUser){
        List<User> search = new ArrayList<>();
        clients.forEach(client -> {
            if( client.getUser().getUserId() != searchUser.getClientId() && client.getUser().getUserName().toLowerCase().startsWith(searchUser.getSearchingFor().toLowerCase())){
                search.add(client.getUser());
            }
        } );
        clients.forEach(client ->{
            if (client.getUser().getUserId() == searchUser.getClientId())
                client.sendMessage(search);
        } );
        clients.forEach(client -> System.out.println("Search result " + client.getUser().getUserName() ) ) ;
    }


    public void createChat(CreateChat createChat) {
        System.out.println("creating " + createChat);
        Chat newChat = new Chat(nextChatId);
        newChat.setChatName(createChat.getChatName());
        newChat.setType(createChat.getChatType());
        increamentNextChatId();
         User creator =  clients.stream().filter(c -> c.getUser().getUserId() == createChat.getClientId() ).toList().get(0).getUser();
         newChat.getParticipants().add(creator);
         if (createChat.getChatType() == ChatType.PRIVATE){
             User added =  clients.stream().filter(c -> c.getUser().getUserId() == createChat.getParticipant() ).toList().get(0).getUser();
              newChat.getParticipants().add(added);
         }
        chats.add(newChat);
        // Send the chat only to the participants
        broadCastChat(newChat,newChat);


    }
    public void sendChatMessage(ChatMessage chatMessage){
        Chat chat = chats.stream()
                .filter(c -> c.getChatId() == chatMessage.getChatId() ).toList().get(0);
        chat.addMessage(chatMessage);
         System.out.println("sending " +chatMessage);
        broadCastChat(chat,chatMessage);
    }

    public void addToChat(AddToGroupChat addToGroupChat){
         Chat chat =  chats.stream()
                .filter(c -> c.getChatId() == addToGroupChat.getChatId())
                .toList().get(0);
         MessagingSession added = clients.stream()
                 .filter(c -> c.getUser().getUserId() == addToGroupChat.getAddedUserId())
                 .toList().get(0);
         List<User> participants = chat.getParticipants();
         participants.add(added.getUser());
        broadCastChat(chat,chat);

    }

      public void broadCastChat(Chat chat, Object send){
        System.out.println("chat messages  " +chat.getMessages());
          List<MessagingSession> clientsToSend = clients
                  .stream().filter(client -> chat.getParticipants().contains(client.getUser()) ).toList();

          clientsToSend.forEach(client ->{
              System.out.println("sending to " + client.getUser());
              client.sendMessage(send);
      });}

    }



