package com.example.ums.DAL.repositories.impl.DirectorImpl;

import com.example.ums.DAL.repositories.DirectorRepository;
import com.example.ums.DAL.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DirectorSQLRepositoryImpl implements DirectorRepository {

    public DirectorSQLRepositoryImpl(){}

    public void create(Map<String, Object> params) {
        String name = (String) params.get("name");
        String surname = (String) params.get("surname");
        String lastname = (String) params.get("lastname");
        String post = (String) params.get("post");

        try {
            // Получение ID поста
            String getPostIdQuery = "Select id from posts where name = ?";
            PreparedStatement getPostIdStmt = DB.DBConnect().prepareStatement(getPostIdQuery);
            getPostIdStmt.setString(1, post);
            ResultSet resultSet = getPostIdStmt.executeQuery();
            resultSet.next();
            int post_id = resultSet.getInt("id");

            // Вставка данных в таблицу workers
            String insertIntoWorkers = "INSERT INTO workers (worker_name, post_id) VALUES (?, ?)";
            PreparedStatement insertWorkersStmt = DB.DBConnect().prepareStatement(insertIntoWorkers);
            insertWorkersStmt.setString(1, name + " " + surname + " " + lastname);
            insertWorkersStmt.setInt(2, post_id);
            insertWorkersStmt.executeUpdate();

            // Вставка данных в таблицу users
            String insertIntoUsers = "Insert into users (worker_id, role_id) select id, post_id from workers order by id desc limit 1";
            PreparedStatement insertUsersStmt = DB.DBConnect().prepareStatement(insertIntoUsers);
            insertUsersStmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void delete(Map<String, Object> params) {
        String workerName = (String) params.get("workerName");

        try {
            // Удаление пользователя из таблицы users
            String deleteUserQuery = "DELETE FROM users WHERE worker_id = (SELECT id FROM workers WHERE worker_name = ?)";
            PreparedStatement deleteUserStmt = DB.DBConnect().prepareStatement(deleteUserQuery);
            deleteUserStmt.setString(1, workerName);
            deleteUserStmt.executeUpdate();

            // Удаление работника из таблицы workers
            String deleteWorkerQuery = "DELETE FROM workers WHERE worker_name = ?";
            PreparedStatement deleteWorkerStmt = DB.DBConnect().prepareStatement(deleteWorkerQuery);
            deleteWorkerStmt.setString(1, workerName);
            deleteWorkerStmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
