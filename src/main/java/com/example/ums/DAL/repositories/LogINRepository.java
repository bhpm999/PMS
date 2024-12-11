package com.example.ums.DAL.repositories;

public interface LogINRepository extends Repository {
    boolean enterSystem(String password,String login);
    boolean registrate(String password,String login);
}
