package com.example.ums.UI;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.WorkerDTO;
import com.example.ums.BLL.Service.AdminBLL;
import com.example.ums.BLL.Service.ClientBLL;
import com.example.ums.BLL.Service.DirectorBLL;
import com.example.ums.BLL.Service.StorekeeperBLL;
import com.example.ums.DAL.Interfaces.ComboBoxInfo;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

//Контроллер странички с добавлением работника
public class AddController extends Initialization implements ComboBoxInfo, ElementsVisibility, Tables {
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
        DirectorBLL directorBLL = new DirectorBLL(this);
        if(name.getText().trim().isEmpty() || surname.getText().trim().isEmpty() ||
                lastname.getText().trim().isEmpty() ||
                choosePost.getValue().isEmpty())
        {
            Alerts.blankDataAlert();
            setDefault();}
        else{
            try {
                Alerts.confirmationAlert();
                directorBLL.createDirector();
                setDefault();
            }catch (CancellationException e){
                return;
            }
        }
    }

    public void choosePost(){
        choosePost.setItems(choosePostList());
        adminAddingPageChoosePost.setItems(choosePostList());
    }
    public void chooseState(){
        storekeeperChooseState.setItems(chooseStateList());
    }
    public void chooseMedicineGroup(){
        storekeeperChooseMedicineGroup.setItems(chooseMedicineGroupList());
    }
    public void chooseProvider(){
        storekeeperChooseProvider.setItems(chooseProviderList(storekeeperChooseState.getValue()));
    }

    public void setInfoTable(){

        AdminBLL adminBLL = new AdminBLL(this);
        setCellProperty(adminWorkerTableMap);
        adminWorkersTable.setItems(adminBLL.readWorkers());
    }
    public void setClientMedicineTable(){
        ClientBLL clientBLL = new ClientBLL();
        setCellProperty(clientMedicineTableMap);
        clientMedicineTable.setItems(clientBLL.readMedicines());
    }
    public void setAdminAddWorkerLoginAndPassword(){
        try {
            Alerts.confirmationAlert();
            AdminBLL updateWorkerLoginAndPassword = new AdminBLL(this);
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
            StorekeeperBLL storekeeperBLL = new StorekeeperBLL(this);
            storekeeperBLL.createMedicine();
        }catch (NumberFormatException e){
            Alerts.blankDataAlert();
        }
        catch (CancellationException ignored){}
    }
    public void setClientMakeOrderButton(){
        try {
            Alerts.confirmationAlert();
            ClientBLL clientBLL = new ClientBLL(this);
            clientBLL.createOrder();
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