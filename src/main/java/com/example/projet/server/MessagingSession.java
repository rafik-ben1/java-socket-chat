package com.example.projet.server;



import com.example.projet.dto.CreateChat;
import com.example.projet.dto.CreateUser;
import com.example.projet.dto.SearchUser;
import com.example.projet.models.ChatMessage;
import com.example.projet.models.Message;
import com.example.projet.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessagingSession implements Runnable {

    private transient Socket socket;
    private transient ObjectOutputStream out;

    private transient ObjectInputStream in;

    private User user;


    private MessageHandler handler = new MessageHandler();


    public MessagingSession(Socket socket) throws IOException {

            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
           in = new ObjectInputStream(socket.getInputStream());
           handler.addClient(this);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run(){
       while (true){
           try {
               Message msg = (Message) in.readObject();
               routeMessages(msg);
           } catch (IOException | ClassNotFoundException e) {
              disconect();
           }
       }
    }
    public void routeMessages(Message message) throws IOException {
        switch (message.getType()){
            case NEW_USER :
                handler.createUser((CreateUser) message);
                break;
            case SEARCH_USER:
                handler.searchUser((SearchUser) message );
                break;
            case CREATE_CHAT:
                handler.createChat((CreateChat) message);
                break;

        }

    }
    public void sendMessage(Object object){
        if (socket.isClosed())
            return;
        try {
            out.writeObject(object);
            out.flush();
        } catch (IOException e) {
            disconect();
        }
    }
    public void disconect(){
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
