package com.example.projet.dto;

import com.example.projet.models.Message;
import com.example.projet.models.enums.MessagesType;


public class SearchUser extends Message {


    private String searchingFor;

    private int requestedBy;

    public int getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }

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
