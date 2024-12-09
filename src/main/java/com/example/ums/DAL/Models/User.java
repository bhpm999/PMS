package com.example.ums.DAL.Models;

public class User {
    public String Id;
    public String login;
    public String password;

    public User(String Id, String login, String password) {
        this.Id = Id;
        this.login = login;
        this.password = password;
    }

    public User() {

    }
    public void print(){
        System.out.println(this.Id + " "+ this.login+" "+this.password);
    }

    public String getId() {
        return Id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
