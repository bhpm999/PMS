package com.example.ums.BLL.Service;

import com.example.ums.DAL.Interfaces.ILogIN;
import com.example.ums.UI.DBs;

public class LogINBLL extends DBSelection{
    public static DBs db;
    public static int roleID;
    public static Object userID;
    ILogIN logIN = select(ILogIN.class,db);
    public boolean enterSystem(String login,String password){
        return logIN.enterSystem(login,password);
    }
    public boolean registrate(String login, String password){
        return logIN.registrate(login,password);
    }

}
