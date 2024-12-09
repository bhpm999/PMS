package com.example.ums.DAL.Repositories.LogINBCRUD;

import com.example.ums.BLL.Service.LogINBLL;
import com.example.ums.DAL.Interfaces.ILogIN;
import com.example.ums.DAL.Repositories.DB;
import com.example.ums.UI.Alerts;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class LogINCRUDMongo implements ILogIN {
    public boolean enterSystem(String login, String password) {
        boolean is = false;
        MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> usersCollection = database.getCollection("users");

            // Создание запроса для поиска пользователя
            Document query = new Document("login", login).append("password", password);
            Document user = usersCollection.find(query).first();
            if (user != null) {
                is = true;
                LogINBLL.roleID = user.getInteger("role_id");
                LogINBLL.userID = user.getObjectId("_id");
            } else {
                Alerts.notExistAlert();
            }

        return is;
    }



    public boolean registrate(String login, String password) {
        boolean is = false;
        MongoDatabase database = DB.connectToDatabase();

            MongoCollection<Document> usersCollection = database.getCollection("users");

            // Проверка существования пользователя с данным логином
            Document query = new Document("login", login);
            long count = usersCollection.countDocuments(query);

            if (count == 0) {
                // Создание нового документа пользователя
                Document newUser = new Document("login", login)
                        .append("password", password)
                        .append("role_id", 0);

                // Вставка документа в коллекцию
                usersCollection.insertOne(newUser);
                is = true;
            } else {
                Alerts.existAlert();
            }

        return is;
    }

}
