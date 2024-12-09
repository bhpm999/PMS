package com.example.ums.BLL.Service;

import com.example.ums.BLL.DTO.UserDTO;
import com.example.ums.BLL.DTO.WorkerDTO;
import com.example.ums.DAL.Interfaces.IAdmin;
import com.example.ums.DAL.Models.User;
import com.example.ums.DAL.Models.Worker;
import com.example.ums.UI.AddController;
import com.example.ums.UI.DeleteController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class AdminBLL extends DBSelection {
    private AddController addController;
    private DeleteController deleteController;
    public AdminBLL(AddController addController){
        this.addController = addController;
    };
    public AdminBLL(DeleteController deleteController){
        this.deleteController = deleteController;
    }
    public AdminBLL(){};


    private final IAdmin adminCRUD = super.select(IAdmin.class,LogINBLL.db);
    public void updateAdmin() {
        AddController workerInstance = addController;
        String newLogin = workerInstance.adminNewLoginField.getText();
        String newPassword = workerInstance.adminNewPasswordField.getText();
        String workerName = workerInstance.selectedWorker.name;
        Map<String,Object> p = Map.of("newLogin",newLogin,"newPassword",newPassword,"workerName",workerName);
        adminCRUD.update( p);
    }
    public void deleteAdmin(){
        DeleteController userInstance = deleteController;
        String userLogin = userInstance.selectedUser.login;
        String userPassword = userInstance.selectedUser.password;
        Map<String,Object> p = Map.of("userLogin",userLogin,"userPassword",userPassword);
        adminCRUD.delete(p);
    }


    public ObservableList<UserDTO> readUsers() {
        ObservableList<UserDTO> userDTOS = FXCollections.observableArrayList();
        for (User user:
                adminCRUD.readUser()) {

            UserDTO userDTO = new UserDTO(user.getId(),user.getLogin(),user.getPassword());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public ObservableList<WorkerDTO> readWorkers() {
        AddController postInstance = addController;
        String post = postInstance.adminAddingPageChoosePost.getValue();
        Map<String,Object> p = Map.of("post",post);
        ObservableList<WorkerDTO> workerDTOS = FXCollections.observableArrayList();
        for (Worker worker:
             adminCRUD.readWorker(p)) {
            WorkerDTO workerDTO = new WorkerDTO(
                    worker.getName(),worker.getLogin(),worker.getPassword()
            );
            workerDTOS.add(workerDTO);
        }
        return workerDTOS;
    }
}