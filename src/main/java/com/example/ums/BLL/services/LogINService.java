package com.example.ums.BLL.services;

import com.example.ums.DAL.repositories.LogINRepository;
import com.example.ums.UI.DBs;

public class LogINService extends DBSelection{
    public static DBs db;
    public static int roleID;
    public static Object userID;
    LogINRepository logIN = select(LogINRepository.class,db);
    public boolean enterSystem(String login,String password){
        return logIN.enterSystem(login,password);
    }
    public boolean registrate(String login, String password){
        return logIN.registrate(login,password);
    }

}
