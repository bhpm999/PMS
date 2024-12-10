package com.example.ums.BLL.services;

import com.example.ums.DAL.repositories.ComboboxRepository;

import java.util.List;

public class ComboboxService extends DBSelection{
    ComboboxRepository comboboxCRUD = select(ComboboxRepository.class, LogINService.db);
    public List<String> findPosts(){
        return comboboxCRUD.findPosts();
    }
    public  List<String> findStates(){
        return comboboxCRUD.findStates();
    }
    public List<String> findMedicineGroups(){
        return comboboxCRUD.findMedicineGroups();
    }
    public  List<String> findProviders(String state){
        return  comboboxCRUD.findProviders(state);
    }
    public List<String> findMedicines(String group){
        return comboboxCRUD.findMedicines(group);
    }
    public List<String> findWorkers(String post){
        return  comboboxCRUD.findWorkers(post);
    }
}
