package com.example.ums.DAL.Repositories.StorekeeperCRUD;

import com.example.ums.DAL.Interfaces.IStorekeeper;
import com.example.ums.DAL.Repositories.DB;
import com.example.ums.DAL.Models.Medicine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;

import java.util.Arrays;
import java.util.Map;

public class StoreKeeperCRUDMongo implements IStorekeeper {
    public void delete(Map<String, Object> params) {
        String medicine = (String) params.get("medicine");

        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> medicinesCollection = database.getCollection("medicines");

        // Удаление документа из коллекции medicines
        Document query = new Document("medicine_name", medicine);
        medicinesCollection.deleteOne(query);
    }
    public ObservableList<Medicine> readMedicine() {
        ObservableList<Medicine> medicinesList = FXCollections.observableArrayList();

            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> medicinesCollection = database.getCollection("medicines");

            // Выполнение агрегационного запроса
            MongoCursor<Document> cursor = medicinesCollection.aggregate(Arrays.asList(
                    new Document("$lookup", new Document()
                            .append("from", "state")
                            .append("localField", "state_id")
                            .append("foreignField", "id")
                            .append("as", "stateDetails")),
                    new Document("$unwind", "$stateDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "provider")
                            .append("localField", "provider_id")
                            .append("foreignField", "id")
                            .append("as", "providerDetails")),
                    new Document("$unwind", "$providerDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "medicine_groups")
                            .append("localField", "group_id")
                            .append("foreignField", "id")
                            .append("as", "groupDetails")),
                    new Document("$unwind", "$groupDetails"),
                    new Document("$project", new Document()
                            .append("medicine_name", 1)
                            .append("group_name", "$groupDetails.group_name")
                            .append("count", 1)
                            .append("cost", 1)
                            .append("provider_name", "$providerDetails.name")
                            .append("state_name", "$stateDetails.name")),
                    new Document("$sort", new Document("medicine_name", 1).append("group_name", 1))
            )).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Medicine medicine = new Medicine(
                        doc.getString("group_name"),
                        doc.getString("medicine_name"),
                        doc.getInteger("count"),
                        doc.getDouble("cost"),
                        doc.getString("provider_name"),
                        doc.getString("state_name")
                );
                medicinesList.add(medicine);
            }
        return medicinesList;
    }
    public void create(Map<String, Object> params) {
        String medicineName = (String) params.get("medicineName");
        int count = (int) params.get("count");
        double price = (double) params.get("price");
        String state = (String) params.get("state");
        String medicineGroup = (String) params.get("medicineGroup");
        String provider = (String) params.get("provider");

        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> medicinesCollection = database.getCollection("medicines");
        MongoCollection<Document> stateCollection = database.getCollection("state");
        MongoCollection<Document> medicineGroupsCollection = database.getCollection("medicine_groups");
        MongoCollection<Document> providerCollection = database.getCollection("provider");

        // Получение ID state
        Document stateQuery = new Document("name", state);
        Document stateDoc = stateCollection.find(stateQuery).first();
        Integer stateId = stateDoc != null ? stateDoc.getInteger("id") : null;

        // Получение ID medicineGroup
        Document medicineGroupQuery = new Document("group_name", medicineGroup);
        Document medicineGroupDoc = medicineGroupsCollection.find(medicineGroupQuery).first();
        Integer medicineGroupId = medicineGroupDoc != null ? medicineGroupDoc.getInteger("id") : null;

        // Получение ID provider
        Document providerQuery = new Document("name", provider);
        Document providerDoc = providerCollection.find(providerQuery).first();
        Integer providerId = providerDoc != null ? providerDoc.getInteger("id") : null;

        // Вставка данных в коллекцию medicines
        Document medicineDoc = new Document("medicine_name", medicineName)
                .append("count", count)
                .append("cost", price)
                .append("state_id", stateId)
                .append("group_id", medicineGroupId)
                .append("provider_id", providerId);
        medicinesCollection.insertOne(medicineDoc);
    }



}
