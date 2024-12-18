package com.example.ums.DAL.repositories.impl.sql;

import com.example.ums.BLL.services.LogINService;
import com.example.ums.DAL.repositories.LogINRepository;
import com.example.ums.DAL.db.DB;
import com.example.ums.UI.Alerts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogINSQLRepositoryImpl implements LogINRepository {
    public boolean registrate(String login,String password){
        boolean is = false;
        try{
            String check = "Select count(*) from users where login = '"+login+"';";
            PreparedStatement checking = DB.DBConnect().prepareStatement(check);
            ResultSet result = checking.executeQuery();
            result.next();
            if(result.getInt(1) == 0 ) {
                String insert = "INSERT INTO users (login,password,role_id) VALUES(?,?,?)";
                PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setInt(3, 0);
                preparedStatement.executeUpdate();
                is = true;

            }
            else {
                Alerts.existAlert();
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return is;
    }

    @Override
    public boolean enterSystem(String login,String password) {
        boolean is = false;
        try{
            String insert = "select count(*),role_id,id from users where login = '"+login+"' and password = '"+password+"' group by id;";
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            is = resultSet.getInt(1) == 1;
            LogINService.roleID = resultSet.getInt(2);
            LogINService.userID = resultSet.getInt(3);
        }catch(SQLException e){
            Alerts.notExistAlert();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return is;
    }
}
