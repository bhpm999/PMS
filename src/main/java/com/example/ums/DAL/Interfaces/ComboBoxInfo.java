package com.example.ums.DAL.Interfaces;

import com.example.ums.DAL.Repositories.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ComboBoxInfo {
    default ObservableList<String> choosePostList(){
        ObservableList<String> postsList = FXCollections.observableArrayList();
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
    default ObservableList<String> chooseStateList(){
        String insert = "SELECT name FROM state;";
        ObservableList<String> statesList = FXCollections.observableArrayList();
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
    default ObservableList<String> chooseMedicineGroupList(){
        String insert = "SELECT group_name FROM medicine_groups;";
        ObservableList<String> groupsList = FXCollections.observableArrayList();
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
    default ObservableList<String> chooseProviderList(String state){
        String insert = "SELECT provider.name FROM provider join state on" +
                " state.id = provider.state_id" +
                " where state.id = (select id from state where name = '"+state+"');";
        ObservableList<String> providersList = FXCollections.observableArrayList();
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
    default ObservableList<String> chooseMedicineList(String group) {
        String insert = "SELECT medicines.medicine_name FROM medicines join medicine_groups on" +
                " medicine_groups.id = medicines.group_id" +
                " where medicine_groups.group_name = '" + group + "'";
        ObservableList<String> medicinesList = FXCollections.observableArrayList();
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
    default ObservableList<String> chooseWorkerList(String post){
        String insert = "SELECT workers.worker_name FROM " +
                "workers join posts on workers.post_id = posts.id WHERE posts.name = '"+post+"';";
        ObservableList<String> workersList= FXCollections.observableArrayList();
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
