package com.example.ums.DAL.Repositories.PharmacistCRUD;

import com.example.ums.BLL.Increase;
import com.example.ums.DAL.Interfaces.IPharmacist;
import com.example.ums.DAL.Repositories.DB;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class PharmacistCRUDSQL implements IPharmacist {
    public PharmacistCRUDSQL(){}

    @Override
    public ObservableList<Medicine> readMedicines() {
        String insert = "select medicines.medicine_name, sum(orders.count) as  count, medicines.cost,state.name as state_name,medicine_groups.group_name,provider.name as provider_name from\n" +
                "medicines join medicine_groups on medicines.group_id = medicine_groups.id\n" +
                "join orders on medicines.id = orders.medicine_id\n" +
                "join state on medicines.state_id = state.id\n" +
                "join provider on medicines.provider_id = provider.id\n" +
                "where orders.status_id = 3\n" +
                "group by medicines.id";
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
    public ObservableList<Order> readOrders(){
        String insert = "select orders.id, medicines.medicine_name,provider.name as provider_name,state.name as state_name,orders.count,round(medicines.cost*"+ Increase.increase+",2)*orders.count as cost,status.name as status " +
                "from medicines join provider on medicines.provider_id = provider.id " +
                "join state on medicines.state_id = state.id " +
                "join orders on orders.medicine_id = medicines.id " +
                "join status on orders.status_id = status.id " +
                "where status.id = 1";
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getNString("medicine_name"),
                        resultSet.getNString("provider_name"), resultSet.getNString("state_name"),
                        resultSet.getInt("count"),resultSet.getDouble("cost"),resultSet.getNString("status"),
                        String.valueOf(resultSet.getInt("id")));
                ordersList.add(order);
            }
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return ordersList;
    }

    public void update(Map<String, Object> params) {
        Integer id = (Integer) params.get("id");

        String query = "UPDATE orders SET status_id = 3 WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
