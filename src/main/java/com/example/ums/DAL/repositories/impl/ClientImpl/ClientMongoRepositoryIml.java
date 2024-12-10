package com.example.ums.DAL.repositories.impl.ClientImpl;

import com.example.ums.BLL.Increase;
import com.example.ums.BLL.services.LogINService;
import com.example.ums.DAL.repositories.ClientRepository;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.example.ums.DAL.db.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientMongoRepositoryIml implements ClientRepository {
    public void update(Map<String,Object> p) {
        String id = (String) p.get("id");
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> ordersCollection = database.getCollection("orders");

        // Создание запроса для обновления статуса заказа
        Document query = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document("status_id", 1));

        ordersCollection.updateOne(query, update);
    }
    public void create(Map<String, Object> p) {
        List<Medicine> medicines = (List<Medicine>) p.get("medicines");

        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        MongoCollection<Document> medicinesCollection = database.getCollection("medicines");

        for (Medicine m : medicines) {
            Document medicineQuery = new Document("medicine_name", m.getName());
            Document medicineDoc = medicinesCollection.find(medicineQuery).first();

            if (medicineDoc != null) {
                Integer medicineId = medicineDoc.getInteger("id");

                Document orderDoc = new Document("user_id", LogINService.userID)
                        .append("medicine_id", medicineId)
                        .append("status_id", 2)
                        .append("count", m.getCount());

                ordersCollection.insertOne(orderDoc);
            }
        }
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
                            .append("cost", new Document("$round", Arrays.asList(new Document("$multiply", Arrays.asList("$cost", Increase.increase)), 2)))
                            .append("provider_name", "$providerDetails.name")
                            .append("state_name", "$stateDetails.name"))
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
    public ObservableList<Order> readOrder() {
        ObservableList<Order> ordersList = FXCollections.observableArrayList();

            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> ordersCollection = database.getCollection("orders");

            // Выполнение агрегационного запроса
            MongoCursor<Document> cursor = ordersCollection.aggregate(Arrays.asList(
                    new Document("$lookup", new Document()
                            .append("from", "medicines")
                            .append("localField", "medicine_id")
                            .append("foreignField", "id")
                            .append("as", "medicineDetails")),
                    new Document("$unwind", "$medicineDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "provider")
                            .append("localField", "medicineDetails.provider_id")
                            .append("foreignField", "id")
                            .append("as", "providerDetails")),
                    new Document("$unwind", "$providerDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "state")
                            .append("localField", "medicineDetails.state_id")
                            .append("foreignField", "id")
                            .append("as", "stateDetails")),
                    new Document("$unwind", "$stateDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "status")
                            .append("localField", "status_id")
                            .append("foreignField", "id")
                            .append("as", "statusDetails")),
                    new Document("$unwind", "$statusDetails"),
                    new Document("$match", new Document("user_id", LogINService.userID)),
                    new Document("$project", new Document()
                            .append("id", 1)
                            .append("medicine_name", "$medicineDetails.medicine_name")
                            .append("provider_name", "$providerDetails.name")
                            .append("state_name", "$stateDetails.name")
                            .append("count", "$count")
                            .append("cost", new Document("$round", Arrays.asList(new Document("$multiply", Arrays.asList("$medicineDetails.cost", Increase.increase, "$count")), 2)))
                            .append("status", "$statusDetails.name"))
            )).iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Order order = new Order(
                        doc.getString("medicine_name"),
                        doc.getString("provider_name"),
                        doc.getString("state_name"),
                        doc.getInteger("count"),
                        doc.getDouble("cost"),
                        doc.getString("status"),
                        String.valueOf(doc.getObjectId("_id"))
                );
                ordersList.add(order);
            }
        return ordersList;
    }




}
