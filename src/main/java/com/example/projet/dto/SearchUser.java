package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.enums.MessagesType;


public class SearchUser extends Message {


    private String searchingFor;

    public String getSearchingFor() {
        return searchingFor;
    }

    public void setSearchingFor(String searchingFor) {
        this.searchingFor = searchingFor;
    }

    public SearchUser()
    {
        setType(MessagesType.SEARCH_USER);
    }
}
