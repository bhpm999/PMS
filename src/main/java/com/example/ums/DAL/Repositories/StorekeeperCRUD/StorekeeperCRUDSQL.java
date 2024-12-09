package com.example.ums.DAL.Repositories.StorekeeperCRUD;

import com.example.ums.DAL.Interfaces.IStorekeeper;
import com.example.ums.DAL.Repositories.DB;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.UI.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class StorekeeperCRUDSQL implements IStorekeeper {

    public StorekeeperCRUDSQL(){};

    public void delete(Map<String, Object> params) {
        String medicine = (String) params.get("medicine");

        String query = "DELETE FROM medicines WHERE medicine_name = ?";
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(query);
            preparedStatement.setString(1, medicine);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void create(Map<String, Object> params) {
        String medicineName = (String) params.get("medicineName");
        int count = (int) params.get("count");
        double price = (double) params.get("price");
        String state = (String) params.get("state");
        String medicineGroup = (String) params.get("medicineGroup");
        String provider = (String) params.get("provider");

        try {
            String insert = "INSERT INTO medicines SET " +
                    "medicine_name = ?, " +
                    "count = ?, " +
                    "cost = ?, " +
                    "state_id = (SELECT id FROM state WHERE name = ?), " +
                    "group_id = (SELECT id FROM medicine_groups WHERE group_name = ?), " +
                    "provider_id = (SELECT id FROM provider WHERE name = ?)";
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            preparedStatement.setString(1, medicineName);
            preparedStatement.setInt(2, count);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, state);
            preparedStatement.setString(5, medicineGroup);
            preparedStatement.setString(6, provider);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Alerts.blankDataAlert();
        }
    }



    @Override
    public ObservableList<Medicine> readMedicine() {
        String insert = "select medicines.medicine_name,medicine_groups.group_name,medicines.count,medicines.cost,provider.name as provider_name, state.name as state_name from " +
                "medicines join state on state.id = medicines.state_id " +
                "join provider on provider.id = medicines.provider_id " +
                "join medicine_groups on medicines.group_id = medicine_groups.id order by medicine_name,group_name";
        ObservableList<Medicine> medicinesList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getNString("group_name"),
                        resultSet.getNString("medicine_name"),
                        resultSet.getInt("count"),
                        resultSet.getDouble("cost"),
                        resultSet.getString("provider_name"),
                        resultSet.getNString("state_name"));
                medicinesList.add(medicine);
            }
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return medicinesList;
    }
}
