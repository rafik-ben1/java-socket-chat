package com.example.projet;

import com.example.projet.models.Chat;
import com.example.projet.models.User;
import com.example.projet.views.ViewFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    private ObjectProperty<User> selectedProfile = new SimpleObjectProperty<>();

    private ObjectProperty<Chat> selectedChat = new SimpleObjectProperty<>();

    public ObjectProperty<User> selectedProfileProperty() {
        return selectedProfile;
    }

    public ObjectProperty<Chat> selectedChatProperty() {
        return selectedChat;
    }

    private Model(){
        this.viewFactory = new ViewFactory();
    }
    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }
    public ViewFactory getViewFactory(){return viewFactory;}
}
