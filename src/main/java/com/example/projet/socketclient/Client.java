package com.example.projet.socketclient;


import com.example.projet.models.Chat;
import com.example.projet.models.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Client implements Runnable {
    private static Client instance;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private User currentUser;

    private ObjectProperty<List<User>> searchedUsersProperty = new SimpleObjectProperty<>();

    public ObjectProperty<List<User>> searchedUsersProperty() {
        return searchedUsersProperty;
    }
    private ObjectProperty<List<Chat>> myChatsProperty = new SimpleObjectProperty<>();
    public ObjectProperty<List<Chat>> getMyChatsProperty(){return myChatsProperty;}

    public User getUser(){return currentUser;}

    private Client(){
        try {
            socket = new Socket("localhost",5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Client getInstance(){
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }
    public void sendMessage(Object object){
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (true){
            try {
                //Reading the message object

                Object message = in.readObject();
                if(message instanceof Integer ){
                    //Getting the current client id to insure communication between client and server
                    currentUser = new User((Integer) message);
                    System.out.println(currentUser.getUserId());
                } else if (message instanceof User) {
                    //Getting the current User
                    currentUser = (User) message;
                    System.out.println(currentUser.getUserName());

                } else if (message instanceof Chat) {
                    //new chat added
                    List<Chat> chatList = myChatsProperty.get();
                    chatList.add((Chat) message);
                    myChatsProperty.set(chatList);

                } else if(message instanceof List<?>){
                    List<?> list = (List<?>) message;
                     if(list.isEmpty()){
                         continue;
                     }
                    if (((List<?>) message).get(0) instanceof User){
                         searchedUsersProperty.set((List<User>) list );
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
