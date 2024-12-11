package com.example.ums.DAL.repositories.impl.mongo;

import com.example.ums.BLL.services.LogINService;
import com.example.ums.DAL.repositories.LogINRepository;
import com.example.ums.DAL.db.DB;
import com.example.ums.UI.Alerts;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class LogINMongoRepositoryIml implements LogINRepository {
    public boolean enterSystem(String login, String password) {
        boolean is = false;
        MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> usersCollection = database.getCollection("users");

            // Создание запроса для поиска пользователя
            Document query = new Document("login", login).append("password", password);
            Document user = usersCollection.find(query).first();
            if (user != null) {
                is = true;
                LogINService.roleID = user.getInteger("role_id");
                LogINService.userID = user.getObjectId("_id");
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
