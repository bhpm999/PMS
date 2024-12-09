package com.example.ums.DAL.Repositories.AdminCRUD;

import com.example.ums.DAL.Interfaces.IAdmin;
import com.example.ums.DAL.Repositories.DB;
import com.example.ums.DAL.Models.User;
import com.example.ums.DAL.Models.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AdminCRUDSQL implements IAdmin {

    public void update(Map<String, Object> param) {
        Object newLogin = param.get("newLogin");
        Object newPassword = param.get("newPassword");
        Object workerName = param.get("workerName");
        try {
            String insert1 = "UPDATE users JOIN workers  \n" +
                    "ON users.worker_id = workers.id \n" +
                    "SET users.login = '" + newLogin + "', users.password = '" + newPassword + "'\n" +
                    "WHERE workers.worker_name = '" + workerName + "'";
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert1);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(Map<String, Object> param) {
        Object userLogin = param.get("userLogin");
        Object userPassword = param.get("userPassword");
        try {
            String insert2 = "DELETE FROM users WHERE login = '" + userLogin + "' and password = '" + userPassword + "';";
            PreparedStatement preparedStatement1 = DB.DBConnect().prepareStatement(insert2);
            preparedStatement1.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<User> readUser() {
        String insert = "select Id, login, password from users\n" +
                "where role_id = 0;";
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("Id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
                usersList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public ObservableList<Worker> readWorker(Map<String, Object> params) {
        String post = (String) params.get("post");
        String insert = "select workers.worker_name, users.login, users.password from\n" +
                "workers join users on workers.id = users.worker_id\n" +
                "join posts on posts.id = users.role_id\n" +
                "where posts.name = '" + post + "';";
        ObservableList<Worker> workersList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Worker worker = new Worker(resultSet.getString("worker_name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
                workersList.add(worker);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return workersList;
    }





}
