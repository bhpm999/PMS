package com.example.ums.BLL.Service;

import com.example.ums.DAL.Interfaces.ICombobox;

import java.util.List;

public class ComboboxBLL extends DBSelection{
    ICombobox comboboxCRUD = select(ICombobox.class,LogINBLL.db);
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
