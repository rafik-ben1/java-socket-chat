package com.example.projet.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private int port;

    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
           while (true){
               System.out.println("waiting for clients connection");
               Socket socket =  serverSocket.accept();
               System.out.println("connected to a client");
               new Thread(new MessagingSession(socket)).start();
           }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
       Server server = new Server(5000);
    }
}
