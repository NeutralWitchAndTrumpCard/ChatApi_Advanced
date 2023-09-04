package com.springbootchatapi.account;


public class AccountInformation {

    private String id;
    private String name;
    private String email;

    public AccountInformation(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
