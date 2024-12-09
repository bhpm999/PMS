package com.example.ums.DAL.Repositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    public static Connection DBConnect() throws SQLException,ClassNotFoundException{
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/pharmacy",
                "root",
                "12345"
        );
    }
    public static MongoClient mongoClient;
    public static MongoDatabase connectToDatabase() {
        if (mongoClient == null) {
            MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/pharmacy");
            mongoClient = new MongoClient(uri);
        }
        return mongoClient.getDatabase("pharmacy");
    }
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
