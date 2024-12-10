package com.example.ums.UI.controllers;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.WorkerDTO;
import com.example.ums.BLL.services.*;
import com.example.ums.UI.Alerts;
import com.example.ums.UI.ElementsVisibility;
import com.example.ums.UI.Initialization;
import com.example.ums.UI.Tables;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

//Контроллер странички с добавлением работника
public class AddController extends Initialization implements ElementsVisibility, Tables {
    public AnchorPane addWorkerPage;
    public Button addWorkerToDBButton;
    public TextField name;
    public TextField surname;
    public TextField lastname;
    public ComboBox<String> choosePost;
    public TextField adminNewLoginField;
    public TextField adminNewPasswordField;
    public TableView<WorkerDTO> adminWorkersTable;
    public TableColumn<WorkerDTO,String> adminWorkerColumn;
    public TableColumn<WorkerDTO,String> adminWorkerLoginColumn;
    public TableColumn<WorkerDTO,String> adminWorkerPasswordColumn;
    public Button adminSetInfoTableButton;
    public ComboBox<String> adminAddingPageChoosePost;
    public Button adminAddWorkerLoginAndPasswordButton;
    public TextField storekeeperMedicineField;
    public TextField storekeeperCostField;
    public TextField storekeeperCountField;
    public ComboBox<String> storekeeperChooseMedicineGroup;
    public ComboBox<String> storekeeperChooseState;
    public ComboBox<String> storekeeperChooseProvider;
    public Button storekeeperAddProductToDBButton;
    public TableView<MedicineDTO> clientMedicineTable;
    public TableColumn<MedicineDTO,String> clientMedicineNameColumn;
    public TableColumn<MedicineDTO,String> clientMedicineCostColumn;
    public TableColumn<MedicineDTO,String> clientMedicineSateColumn;
    public TableColumn<MedicineDTO,String> clientMedicineProviderColumn;
    public TextField clientMedicineCount;
    public Button clientMakeOrderButton;
    public Label clientCountLable;
    public Button clientAddToOrderButton;
    public TableView<MedicineDTO> clientPreOrderTable;
    public TableColumn<MedicineDTO,String> clientPreOrderMedicineNameColumn;
    public TableColumn<MedicineDTO,String> clientPreOrderedMedicineAmountColumn;
    public Text clientPreOrderedMedicinePrice;

    public WorkerDTO selectedWorker;
    public MedicineDTO selectedMedicine;
    Map<String,TableColumn<MedicineDTO,String>> clientMedicineTableMap;
    Map<String,TableColumn<WorkerDTO,String>> adminWorkerTableMap;
    Map<String,TableColumn<MedicineDTO,String>> clientPreOrderedMedicineTableMap;
    public List<MedicineDTO> chosenMedicine = new ArrayList<>();
    public ComboboxService comboboxService = new ComboboxService();
    public void initialize() {
        adminWorkerTableMap = Map.of("name",adminWorkerColumn,
                "login",adminWorkerLoginColumn, "password",adminWorkerPasswordColumn);
        clientMedicineTableMap = Map.of("name",clientMedicineNameColumn,"cost",
                clientMedicineCostColumn,"state",clientMedicineSateColumn,"provider",clientMedicineProviderColumn);
        clientPreOrderedMedicineTableMap = Map.of("name",clientPreOrderMedicineNameColumn,"count",clientPreOrderedMedicineAmountColumn);
        initialization();
    }
    public void clientLog(){
        setVisibility(addWorkerPage,"client");
        setClientMedicineTable();
        setClientPreOrderTable();
        clientMedicineTable.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getClickCount() == 1 && clientMedicineTable.getSelectionModel().getSelectedItem() != null) {
                    selectedMedicine = clientMedicineTable.getSelectionModel().getSelectedItem();
                }
            }catch (NullPointerException ignored){}
            clientMedicineCount.setText("");
        });
    }
    public void adminLog(){
        setVisibility(addWorkerPage,"admin");
        adminWorkersTable.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getClickCount() == 1) {
                    selectedWorker = adminWorkersTable.getSelectionModel().getSelectedItem();
                }
            }catch (NullPointerException ignored){}
        });
    }
    public void storekeeperLog(){
        setVisibility(addWorkerPage, "storekeeper");
    }

    public void directorLog(){
        setVisibility(addWorkerPage,"director");
    }
    public void setDefault(){
        name.setText("");
        surname.setText("");
        lastname.setText("");
        choosePost.setValue("");
    }
    public void addWorkerToDB() {
        DirectorService directorService = new DirectorService(this);
        if(name.getText().trim().isEmpty() || surname.getText().trim().isEmpty() ||
                lastname.getText().trim().isEmpty() ||
                choosePost.getValue().isEmpty())
        {
            Alerts.blankDataAlert();
            setDefault();}
        else{
            try {
                Alerts.confirmationAlert();
                directorService.createDirector();
                setDefault();
            }catch (CancellationException e){
                return;
            }
        }
    }

    public void choosePost(){
        choosePost.setItems(FXCollections.observableArrayList(
                comboboxService.findPosts()
        ));
        adminAddingPageChoosePost.setItems(FXCollections.observableArrayList(
                comboboxService.findPosts()
        ));
    }
    public void chooseState(){
        storekeeperChooseState.setItems(FXCollections.observableArrayList(
                comboboxService.findStates()
        ));
    }
    public void chooseMedicineGroup(){
        storekeeperChooseMedicineGroup.setItems(FXCollections.observableArrayList(
                comboboxService.findMedicineGroups()
        ));
    }
    public void chooseProvider(){
        storekeeperChooseProvider.setItems(FXCollections.observableArrayList(
                comboboxService.findProviders(storekeeperChooseState.getValue())
                ));
    }

    public void setInfoTable(){

        AdminService adminService = new AdminService(this);
        setCellProperty(adminWorkerTableMap);
        adminWorkersTable.setItems(adminService.readWorkers());
    }
    public void setClientMedicineTable(){
        ClientService clientService = new ClientService();
        setCellProperty(clientMedicineTableMap);
        clientMedicineTable.setItems(clientService.readMedicines());
    }
    public void setAdminAddWorkerLoginAndPassword(){
        try {
            Alerts.confirmationAlert();
            AdminService updateWorkerLoginAndPassword = new AdminService(this);
            updateWorkerLoginAndPassword.updateAdmin();
            setInfoTable();
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }catch (CancellationException e){
            return;
        }
    }
    public void setStorekeeperAddProductToDBButton(){
        try {
            Alerts.confirmationAlert();
            StorekeeperService storekeeperService = new StorekeeperService(this);
            storekeeperService.createMedicine();
            storekeeperCostField.setText("");
            storekeeperCountField.setText("");
            storekeeperMedicineField.setText("");
            storekeeperChooseMedicineGroup.setValue("");
            storekeeperChooseProvider.setValue("");
            storekeeperChooseState.setValue("");
        }catch (NumberFormatException e){
            Alerts.blankDataAlert();
        }
        catch (CancellationException ignored){}
    }
    public void setClientMakeOrderButton(){
        try {
            Alerts.confirmationAlert();
            ClientService clientService = new ClientService(this);
            clientService.createOrder();
            chosenMedicine.clear();
            selectedMedicine = null;
            clientPreOrderTable.setItems(FXCollections.observableArrayList(chosenMedicine));
            clientPreOrderTable.refresh();
            clientMedicineCount.setText("");
            clientPreOrderedMedicinePrice.setText("");
        }catch (CancellationException e){
            return;
        }
    }
    public void setClientAddToOrderButton(){
        try {
            if(selectedMedicine != null)  {
                selectedMedicine.setCount(Integer.parseInt(clientMedicineCount.getText()));
                chosenMedicine.add(selectedMedicine);
            }
        }catch(NumberFormatException e) {
            Alerts.blankDataAlert();
        }

        selectedMedicine = null;
        clientPreOrderTable.setItems(FXCollections.observableArrayList(chosenMedicine));
        clientPreOrderTable.refresh();
        clientPreOrderedMedicinePrice.setText(Double.toString(clientSumOfPreOrderedMedicinePrice(chosenMedicine)));

    }
    public void setClientPreOrderTable(){
        setCellProperty(clientPreOrderedMedicineTableMap);
    }
    public double clientSumOfPreOrderedMedicinePrice(List<MedicineDTO> preOrderMedicine){
        double preOrderedMedicinePrice = 0.00;
        for (MedicineDTO m:preOrderMedicine) {
            preOrderedMedicinePrice = (m.getCost()*m.getCount())+preOrderedMedicinePrice;
        }
        return preOrderedMedicinePrice;
    }

}