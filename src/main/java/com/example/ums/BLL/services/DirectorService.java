package com.example.ums.BLL.services;

import com.example.ums.DAL.repositories.DirectorRepository;
import com.example.ums.UI.controllers.AddController;
import com.example.ums.UI.controllers.DeleteController;

import java.util.Map;

public class DirectorService extends DBSelection {
    private AddController addController;
    private DeleteController deleteController;
    public DirectorService(AddController addController){
        this.addController = addController;
    }
    public DirectorService(DeleteController deleteController){
        this.deleteController = deleteController;
    }
    public DirectorService(){}
    private DirectorRepository directorCRUD = select(DirectorRepository.class, LogINService.db);
    public void createDirector() {
        AddController workerInstance = addController;
        String post = workerInstance.choosePost.getValue();
        String name = workerInstance.name.getText();
        String surname = workerInstance.surname.getText();
        String lastname = workerInstance.lastname.getText();

        Map<String, Object> p = Map.of(
                "name", name,
                "surname", surname,
                "lastname", lastname,
                "post", post
        );
        directorCRUD.create(p);
    }
    public void deleteDirector() {
        DeleteController workerInstance = deleteController;
        String workerName = workerInstance.chooseWorker.getValue();

        Map<String, Object> params = Map.of(
                "workerName", workerName
        );
        directorCRUD.delete(params);
    }


}
