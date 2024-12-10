package com.example.ums.DAL.repositories.impl.DirectorImpl;

import com.example.ums.DAL.repositories.DirectorRepository;
import com.example.ums.DAL.db.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Map;

public class DirectorMongoRepositoryIml implements DirectorRepository {
    public void create(Map<String, Object> params) {
        String name = (String) params.get("name");
        String surname = (String) params.get("surname");
        String lastname = (String) params.get("lastname");
        String post = (String) params.get("post");

        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> workersCollection = database.getCollection("workers");
        MongoCollection<Document> postsCollection = database.getCollection("posts");
        MongoCollection<Document> usersCollection = database.getCollection("users");

        // Получение ID поста
        Document postQuery = new Document("name", post);
        Document postDoc = postsCollection.find(postQuery).first();
        if (postDoc != null) {
            Integer post_id = postDoc.getInteger("id");

            // Вставка данных в коллекцию workers
            Document workerDoc = new Document("worker_name", name + " " + surname + " " + lastname)
                    .append("post_id", post_id);
            workersCollection.insertOne(workerDoc);

            // Получение ID нового работника
            ObjectId workerId = workerDoc.getObjectId("_id");

            // Вставка данных в коллекцию users
            Document userDoc = new Document("worker_id", workerId)
                    .append("role_id", post_id);
            usersCollection.insertOne(userDoc);
        }
    }
    public void delete(Map<String, Object> params) {
        String workerName = (String) params.get("workerName");

        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> usersCollection = database.getCollection("users");
        MongoCollection<Document> workersCollection = database.getCollection("workers");

        // Удаление пользователя из коллекции users
        Document workerQuery = new Document("worker_name", workerName);
        Document workerDoc = workersCollection.find(workerQuery).first();
        if (workerDoc != null) {
            ObjectId workerId = workerDoc.getObjectId("_id");

            // Удаление документов в users
            Document userQuery = new Document("worker_id", workerId);
            usersCollection.deleteOne(userQuery);

            // Удаление документа в workers
            workersCollection.deleteOne(workerQuery);
        }
    }


}
