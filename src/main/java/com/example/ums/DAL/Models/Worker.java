package com.example.ums.DAL.Models;

public class Worker {
    public String name;
    public String login;
    public String password;

    public Worker(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Worker() {

    }
    public void print(){
        System.out.println(this.name + " "+ this.login+" "+this.password);
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}