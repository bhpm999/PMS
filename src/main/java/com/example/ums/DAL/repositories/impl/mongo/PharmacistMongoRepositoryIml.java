package com.example.ums.DAL.repositories.impl.mongo;

import com.example.ums.BLL.Increase;
import com.example.ums.DAL.repositories.PharmacistRepository;
import com.example.ums.DAL.db.DB;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Map;

public class PharmacistMongoRepositoryIml implements PharmacistRepository {
    @Override
    public void update(Map<String, Object> params) {
        String orderId = (String) params.get("id");
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        Document query = new Document("_id", new ObjectId(orderId));
        Document update = new Document("$set", new Document("status_id", 3));
        ordersCollection.updateOne(query, update);
    }

    @Override
    public ObservableList<Order> readOrders() {
        MongoDatabase database = DB.connectToDatabase();
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        ObservableList<Order> ordersList = FXCollections.observableArrayList();

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
                new Document("$match", new Document("status_id", 1)),
                new Document("$project", new Document()
                        .append("date", "$date")
                        .append("_id", "$_id")
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
                    String.valueOf(doc.getObjectId("_id")),
                    doc.getDate("date")
            );
            ordersList.add(order);
        }

        return ordersList;
    }


    @Override
    public ObservableList<Medicine> readMedicines() {
        ObservableList<Medicine> medicinesList = FXCollections.observableArrayList();

            MongoDatabase database = DB.connectToDatabase();
            MongoCollection<Document> medicinesCollection = database.getCollection("medicines");

            // Выполнение агрегационного запроса
            MongoCursor<Document> cursor = medicinesCollection.aggregate(Arrays.asList(
                    new Document("$lookup", new Document()
                            .append("from", "medicine_groups")
                            .append("localField", "group_id")
                            .append("foreignField", "id")
                            .append("as", "groupDetails")),
                    new Document("$unwind", "$groupDetails"),
                    new Document("$lookup", new Document()
                            .append("from", "orders")
                            .append("localField", "id")
                            .append("foreignField", "medicine_id")
                            .append("as", "orderDetails")),
                    new Document("$unwind", "$orderDetails"),
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
                    new Document("$match", new Document("orderDetails.status_id", 3)),
                    new Document("$group", new Document()
                            .append("_id", "$id")
                            .append("medicine_name", new Document("$first", "$medicine_name"))
                            .append("count", new Document("$sum", "$orderDetails.count"))
                            .append("cost", new Document("$first", "$cost"))
                            .append("state_name", new Document("$first", "$stateDetails.name"))
                            .append("group_name", new Document("$first", "$groupDetails.group_name"))
                            .append("provider_name", new Document("$first", "$providerDetails.name"))
                    )
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



}

