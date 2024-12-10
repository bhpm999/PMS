package com.example.ums.DAL.repositories.impl.ClientImpl;

import com.example.ums.BLL.Increase;
import com.example.ums.BLL.services.LogINService;
import com.example.ums.DAL.repositories.ClientRepository;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.example.ums.DAL.db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ClientSQLRepositoryImpl implements ClientRepository {

    @Override
    public ObservableList<Order> readOrder() {
        String insert =  "select orders.id, medicines.medicine_name,provider.name as provider_name,state.name as state_name,orders.count,round(medicines.cost*"+ Increase.increase+",2)*orders.count as cost,status.name as status " +
                "from medicines join provider on medicines.provider_id = provider.id " +
                "join state on medicines.state_id = state.id " +
                "join orders on orders.medicine_id = medicines.id " +
                "join status on orders.status_id = status.id " +
                "where user_id = " + LogINService.userID;
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

    @Override
    public  ObservableList<Medicine> readMedicine() {
        String insert = "select medicines.medicine_name,medicine_groups.group_name,medicines.count,round(medicines.cost*1.3,2) as cost,provider.name as provider_name, state.name as state_name from " +
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


    public void update(Map<String,Object> p) {
            Integer id = (Integer) p.get("id");
            String insert1 = "UPDATE orders set status_id = 1 where id = " + id;
            try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert1);
            preparedStatement.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void create(Map<String, Object> p) {
        List<Medicine> medicines = (List<Medicine>)p.get("medicines");

        try {
            for (Medicine m : medicines) {
                String insert1 = "insert into orders set " +
                        "user_id = " + LogINService.userID + ", " +
                        "medicine_id = (select id from medicines where medicine_name =  '" + m.getName() + "'), " +
                        "status_id = 2, " +
                        "count = " + m.getCount();
                PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert1);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
