package com.example.ums.DAL.repositories.impl.AdminImpl;

import com.example.ums.DAL.repositories.AdminRepository;
import com.example.ums.DAL.db.DB;
import com.example.ums.DAL.Models.User;
import com.example.ums.DAL.Models.Worker;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Map;

public class AdminMongoRepositoryIml implements AdminRepository {
    @Override
    public ObservableList<Worker> readWorker(Map<String, Object> params) {
        ObservableList<Worker> workersList = FXCollections.observableArrayList();
        String post = (String) params.get("post");
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> workersCollection = database.getCollection("workers");

        // Аггрегация для получения работников с ролью 'Аптекарь'
        MongoCursor<Document> cursor = workersCollection.aggregate(Arrays.asList(
                new Document("$lookup", new Document()
                        .append("from", "posts")
                        .append("localField", "post_id")
                        .append("foreignField", "id")
                        .append("as", "postDetails")),
                new Document("$unwind", "$postDetails"),
                new Document("$match", new Document("postDetails.name", post)),
                new Document("$lookup", new Document()
                        .append("from", "users")
                        .append("localField", "_id")
                        .append("foreignField", "worker_id")
                        .append("as", "userDetails")),
                new Document("$unwind", "$userDetails"),
                new Document("$project", new Document()
                        .append("worker_name", 1)
                        .append("userDetails.login", 1)
                        .append("userDetails.password", 1)
                        .append("_id", 0))
        )).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String workerName = doc.getString("worker_name");
            Document userDetails = (Document) doc.get("userDetails");
            String login = userDetails.getString("login");
            String password = userDetails.getString("password");
            Worker worker = new Worker(workerName, login, password);
            workersList.add(worker);
        }
        return workersList;
    }
    @Override
    public  ObservableList<User> readUser() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> usersCollection = database.getCollection("users");

        // Выполнение запроса find
        Document query = new Document("role_id", 0);
        Document projection = new Document("id", 1)
                .append("login", 1)
                .append("password", 1)
                .append("_id", 1);

        MongoCursor<Document> cursor = usersCollection.find(query).projection(projection).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            User user = new User(
                    String.valueOf(doc.getObjectId("_id")),
                    doc.getString("login"),
                    doc.getString("password")
            );
            usersList.add(user);
        }
        return usersList;
    }

    @Override
    public void delete(Map<String, Object> params) {
        String userLogin = (String) params.get("userLogin");
        String userPassword = (String) params.get("userPassword");
        MongoDatabase mongoDatabase = DB.connectToDatabase();
        MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
        Document query = new Document("login", userLogin).append("password", userPassword);
        usersCollection.deleteOne(query);
    }

    @Override
    public void update(Map<String, Object> params) {
        String workerName = (String) params.get("workerName");
        String newLogin = (String) params.get("newLogin");
        String newPassword = (String) params.get("newPassword");
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> usersCollection = database.getCollection("users");
        MongoCollection<Document> workersCollection = database.getCollection("workers");
        Document workersQuery = new Document("worker_name", workerName);
        try (MongoCursor<Document> cursor = workersCollection.find(workersQuery).iterator())
        {
            while (cursor.hasNext())
            {
                Document workerDoc = cursor.next();
                ObjectId workerId = workerDoc.getObjectId("_id");
                // Шаг 2: Обновить документы пользователей с найденным worker_id
                Document usersQuery = new Document("worker_id", workerId);
                Document update = new Document("$set", new Document("login", newLogin).append("password", newPassword));
                usersCollection.updateOne(usersQuery, update);
            }
        }
    }
}
