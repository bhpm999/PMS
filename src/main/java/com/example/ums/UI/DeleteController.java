package com.example.ums.UI;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.UserDTO;
import com.example.ums.BLL.Service.*;
import com.example.ums.DAL.Interfaces.ComboBoxInfo;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.Map;
import java.util.concurrent.CancellationException;

public class DeleteController extends Initialization implements ComboBoxInfo, ElementsVisibility, Tables{
    public AnchorPane deleteWorkerPage;
    public ComboBox<String> choosePost;
    public ComboBox<String> chooseWorker;
    public Button deleteWorkerFromDBButton;
    public TableView<UserDTO> adminUsersTable;
    public TableColumn<UserDTO,String> adminUserIdColumn;
    public TableColumn<UserDTO,String> adminUserLoginColumn;
    public TableColumn<UserDTO,String> adminUserPasswordColumn;
    public Button adminDeleteUserAccountButton;
    public Button adminShowInfoButton;
    public ComboBox<String> storekeeperChooseGroup;
    public ComboBox<String> storekeeperChooseMedicine;
    public Button storekeeperDeleteMedicineFromDBButton;
    public TableView<MedicineDTO> pharmacistSoldProductTable;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductNameColumn;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductCountColumn;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductGroupColumn;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductCostColumn;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductProviderColumn;
    public TableColumn<MedicineDTO,String> pharmacistSoldProductStateColumn;

    public UserDTO selectedUser;
    Map<String,TableColumn<UserDTO,String>> adminUserTableMap;
    Map<String,TableColumn<MedicineDTO,String>> pharmacistMedicineTableMap;
    ComboboxBLL comboboxCRUD = new ComboboxBLL();

    public void initialize() {
        adminUserTableMap = Map.of("Id",adminUserIdColumn,
                "login",adminUserLoginColumn, "password",adminUserPasswordColumn);
        pharmacistMedicineTableMap = Map.of("group",pharmacistSoldProductGroupColumn,"name",pharmacistSoldProductNameColumn,
                "count", pharmacistSoldProductCountColumn,"cost", pharmacistSoldProductCostColumn,"provider",pharmacistSoldProductProviderColumn,
                "state",pharmacistSoldProductStateColumn);
        initialization();
    }
    public void clientLog(){

    }
    @Override
    public void adminLog(){
        setVisibility(deleteWorkerPage,"admin");
        adminUsersTable.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getClickCount() == 1) {
                    selectedUser = adminUsersTable.getSelectionModel().getSelectedItem();
                }
            }catch (NullPointerException ignored){}
        });
    }
    @Override
    public void storekeeperLog(){
        setVisibility(deleteWorkerPage,"storeKeeper");
    }
    public void pharmacistLog(){
        setVisibility(deleteWorkerPage,"pharmacist");
        setPharmacistSoldProductTable();
    }
    public void directorLog(){
        setVisibility(deleteWorkerPage,"director");
    }
    public void choosePost(){
        choosePost.setItems(FXCollections.observableArrayList(
                comboboxCRUD.findPosts()
        ));
    }
    public void chooseWorker() {
        chooseWorker.setItems(FXCollections.observableArrayList(
                comboboxCRUD.findWorkers(choosePost.getValue())
        ));
    }
    public void chooseMedicineGroup(){
        storekeeperChooseGroup.setItems(FXCollections.observableArrayList(
                comboboxCRUD.findMedicineGroups()
        ));
    }
    public void chooseMedicine(){
        storekeeperChooseMedicine.setItems(FXCollections.observableArrayList(
                comboboxCRUD.findMedicines(storekeeperChooseGroup.getValue())
        ));
    }


    public void deleteWorkerFromDBButton(){
        try {
            Alerts.confirmationAlert();
            DirectorBLL directorBLL = new DirectorBLL(this);
            directorBLL.deleteDirector();
            choosePost.setValue("");
            chooseWorker.setValue("");
        }catch (CancellationException e){
            return;
        }
    }
    public void setStorekeeperDeleteMedicineFromDBButton(){
        try {
            Alerts.confirmationAlert();
            StorekeeperBLL storekeeperBLL = new StorekeeperBLL(this);
            storekeeperBLL.deleteMedicine();
        }catch (CancellationException e){
            return;
        }

    }
    public void setInfoTable(){
        AdminBLL adminBLL = new AdminBLL(this);
        setCellProperty(adminUserTableMap);
        adminUsersTable.setItems(adminBLL.readUsers());
    }
    public void setAdminDeleteUserAccountButton(){
        try {
            Alerts.confirmationAlert();
            AdminBLL deleteUserAccount = new AdminBLL(this);
            deleteUserAccount.deleteAdmin();
            setInfoTable();
        }catch (NullPointerException e){
            Alerts.blankDataAlert();
        }
        catch (CancellationException ignored){
        }
    }

    public void setPharmacistSoldProductTable() {
        PharmacistBLL pharmacistBLL = new PharmacistBLL();
        setCellProperty(pharmacistMedicineTableMap);
        pharmacistSoldProductTable.setItems(pharmacistBLL.readMedicine());
    }
}