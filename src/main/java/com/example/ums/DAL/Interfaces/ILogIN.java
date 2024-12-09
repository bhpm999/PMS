package com.example.ums.DAL.Interfaces;

public interface ILogIN extends ICRUD{
    boolean enterSystem(String password,String login);
    boolean registrate(String password,String login);
}
