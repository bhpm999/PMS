package com.example.ums.DAL.repositories.impl.sql;

import com.example.ums.DAL.repositories.ComboboxRepository;
import com.example.ums.DAL.db.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComboboxSQLRepositoryImpl implements ComboboxRepository {
    @Override
    public List<String> findPosts() {
        List<String> postsList = new ArrayList<>();
        String insert = "SELECT name,id FROM posts;";
        try{
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String post_name = resultSet.getNString("name");
                postsList.add(post_name);
            }
        }
        catch (SQLException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return postsList;
    }

    @Override
    public List<String> findStates() {
        String insert = "SELECT name FROM state;";
        List<String> statesList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String stateName = resultSet.getNString("name");
                statesList.add(stateName);
            }
        }
        catch (SQLException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return statesList;
    }

    @Override
    public List<String> findMedicineGroups() {
        String insert = "SELECT group_name FROM medicine_groups;";
        List<String> groupsList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String groupName = resultSet.getNString("group_name");
                groupsList.add(groupName);
            }
        }
        catch (SQLException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return groupsList;
    }

    @Override
    public List<String> findProviders(String state) {
        String insert = "SELECT provider.name FROM provider join state on" +
                " state.id = provider.state_id" +
                " where state.id = (select id from state where name = '"+state+"');";
        List<String> providersList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String provider_name = resultSet.getNString("name");
                providersList.add(provider_name);
            }
        }
        catch (SQLException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return providersList;
    }

    @Override
    public List<String> findMedicines(String group) {
        String insert = "SELECT medicines.medicine_name FROM medicines join medicine_groups on" +
                " medicine_groups.id = medicines.group_id" +
                " where medicine_groups.group_name = '" + group + "'";
        List<String> medicinesList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String medicine_name = resultSet.getNString("medicine_name");
                medicinesList.add(medicine_name);
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return medicinesList;
    }

    @Override
    public List<String> findWorkers(String post) {
        String insert = "SELECT workers.worker_name FROM " +
                "workers join posts on workers.post_id = posts.id WHERE posts.name = '"+post+"';";
        List<String> workersList= new ArrayList<>();
        try{
            PreparedStatement preparedStatement = DB.DBConnect().prepareStatement(insert);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String item = resultSet.getNString("worker_name");
                workersList.add(item);
            }
        }
        catch (SQLException | ClassNotFoundException | NullPointerException e){
            e.printStackTrace();
        }
        return workersList;
    }
}
