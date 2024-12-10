package com.example.ums.DAL.Repositories.ComboboxCRUD;

import com.example.ums.DAL.Interfaces.ICombobox;
import com.example.ums.DAL.Repositories.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ComboboxCRUDMongo implements ICombobox {
    @Override
    public List<String> findPosts() {
        List<String> postsList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> postsCollection = database.getCollection("posts");

            // Выполнение запроса на получение всех документов из коллекции "posts"
            MongoCursor<Document> cursor = postsCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String post_name = doc.getString("name");
                postsList.add(post_name);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postsList;
    }


    @Override
    public List<String> findStates() {
        List<String> statesList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> statesCollection = database.getCollection("state");

            // Выполнение запроса на получение всех документов из коллекции "state"
            MongoCursor<Document> cursor = statesCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String stateName = doc.getString("name");
                statesList.add(stateName);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statesList;
    }


    @Override
    public List<String> findMedicineGroups() {
        List<String> groupsList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> medicineGroupsCollection = database.getCollection("medicine_groups");

            // Выполнение запроса на получение всех документов из коллекции "medicine_groups"
            MongoCursor<Document> cursor = medicineGroupsCollection.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String groupName = doc.getString("group_name");
                groupsList.add(groupName);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return groupsList;
    }


    @Override
    public List<String> findProviders(String state) {
        List<String> providersList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> stateCollection = database.getCollection("state");
            MongoCollection<Document> providerCollection = database.getCollection("provider");

            // Поиск состояния по имени
            Document stateQuery = new Document("name", state);
            Document stateDoc = stateCollection.find(stateQuery).first();

            if (stateDoc != null) {
                Integer stateId = stateDoc.getInteger("id");

                // Поиск провайдеров по state_id
                Document providerQuery = new Document("state_id", stateId);
                MongoCursor<Document> cursor = providerCollection.find(providerQuery).iterator();

                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String providerName = doc.getString("name");
                    providersList.add(providerName);
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return providersList;
    }


    @Override
    public List<String> findMedicines(String group) {
        List<String> medicinesList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> medicineGroupsCollection = database.getCollection("medicine_groups");
            MongoCollection<Document> medicinesCollection = database.getCollection("medicines");

            // Поиск группы лекарств по имени
            Document groupQuery = new Document("group_name", group);
            Document groupDoc = medicineGroupsCollection.find(groupQuery).first();

            if (groupDoc != null) {
                Integer groupId = groupDoc.getInteger("id");

                // Поиск лекарств по group_id
                Document medicineQuery = new Document("group_id", groupId);
                MongoCursor<Document> cursor = medicinesCollection.find(medicineQuery).iterator();

                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String medicineName = doc.getString("medicine_name");
                    medicinesList.add(medicineName);
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicinesList;
    }


    @Override
    public List<String> findWorkers(String post) {
        List<String> workersList = new ArrayList<>();

        try {
            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> postsCollection = database.getCollection("posts");
            MongoCollection<Document> workersCollection = database.getCollection("workers");

            // Поиск поста по имени
            Document postQuery = new Document("name", post);
            Document postDoc = postsCollection.find(postQuery).first();

            if (postDoc != null) {
                Integer postId = postDoc.getInteger("id");

                // Поиск работников по post_id
                Document workerQuery = new Document("post_id", postId);
                MongoCursor<Document> cursor = workersCollection.find(workerQuery).iterator();

                while (cursor.hasNext()) {
                    Document doc = cursor.next();
                    String workerName = doc.getString("worker_name");
                    workersList.add(workerName);
                }

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return workersList;
    }

}
