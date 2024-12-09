package com.example.ums.BLL.Service;

import com.example.ums.DAL.Interfaces.IDirector;
import com.example.ums.UI.AddController;
import com.example.ums.UI.DeleteController;

import java.util.Map;

public class DirectorBLL extends DBSelection {
    private AddController addController;
    private DeleteController deleteController;
    public DirectorBLL(AddController addController){
        this.addController = addController;
    }
    public DirectorBLL(DeleteController deleteController){
        this.deleteController = deleteController;
    }
    public DirectorBLL(){}
    private IDirector directorCRUD = select(IDirector.class,LogINBLL.db);
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
