package com.example.ums.UI.controllers;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.OrderDTO;
import com.example.ums.BLL.services.ClientService;
import com.example.ums.BLL.services.PharmacistService;
import com.example.ums.BLL.services.StorekeeperService;
import com.example.ums.UI.Alerts;
import com.example.ums.UI.ElementsVisibility;
import com.example.ums.UI.Initialization;
import com.example.ums.UI.Tables;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.util.Map;
import java.util.concurrent.CancellationException;

//Контроллер странички с просмотром информации о преподавателе
public class ViewInfoController extends Initialization implements ElementsVisibility, Tables {

    public TableView<MedicineDTO> storekeeperMedicineTable;
    public TableColumn<MedicineDTO,String> storekeeperMedicineGroupColumn;
    public TableColumn<MedicineDTO,String> storekeeperMedicineNameColumn;
    public TableColumn<MedicineDTO,String> storekeeperMedicineCountColumn;
    public TableColumn<MedicineDTO,String> storekeeperMedicineCostColumn;
    public TableColumn<MedicineDTO,String> storekeeperMedicineProviderColumn;
    public TableColumn<MedicineDTO,String> storekeeperMedicineStateColumn;

    public TableView<OrderDTO> clientOrderTable;
    public TableColumn<OrderDTO,String> clientOrderNameColumn;
    public TableColumn<OrderDTO,String> clientOrderProviderColumn;
    public TableColumn<OrderDTO,String> clientOrderSateColumn;
    public TableColumn<OrderDTO,String> clientOrderCountColumn;
    public TableColumn<OrderDTO,String> clientOrderCostColumn;
    public TableColumn<OrderDTO,String> clientOrderStatusColumn;
    public TableColumn<OrderDTO,String> clientOrderIdColumn;
    public Button clientPaymentButton;

    public TableView<OrderDTO> pharmacistOrderTable;
    public TableColumn<OrderDTO,String> pharmacistOrderNameColumn;
    public TableColumn<OrderDTO,String> pharmacistOrderProviderColumn;
    public TableColumn<OrderDTO,String> pharmacistOrderStateColumn;
    public TableColumn<OrderDTO,String> pharmacistOrderCountColumn;
    public TableColumn<OrderDTO,String> pharmacistOrderIdColumn;
    public Button pharmacistSendOrderButton;
    public AnchorPane viewInfoAboutTeacherPage;
    public OrderDTO selectedOrder;
    Map<String,TableColumn<OrderDTO,String>> pharmacistOrderTableMap;
    Map<String,TableColumn<OrderDTO,String>> clientOrderTableMap;
    Map<String,TableColumn<MedicineDTO,String>> storeKeeperTableMap;

    public void initialize() {
        pharmacistOrderTableMap = Map.of("name",pharmacistOrderNameColumn,"provider",pharmacistOrderProviderColumn,"state",pharmacistOrderStateColumn,
                "count",pharmacistOrderCountColumn,"id",pharmacistOrderIdColumn);
        clientOrderTableMap = Map.of("cost",clientOrderCostColumn,"name",clientOrderNameColumn,"provider",clientOrderProviderColumn,
                "state", clientOrderSateColumn,"status",clientOrderStatusColumn, "count",clientOrderCountColumn, "id", clientOrderIdColumn);
        storeKeeperTableMap = Map.of("group",storekeeperMedicineGroupColumn,"name",storekeeperMedicineNameColumn,"count",
                storekeeperMedicineCountColumn,"cost",storekeeperMedicineCostColumn, "provider",storekeeperMedicineProviderColumn, "state", storekeeperMedicineStateColumn);
        initialization();

    }
    public void clientLog(){
        setVisibility(viewInfoAboutTeacherPage,"client");
        setClientOrderTable();
        clientOrderTable.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getClickCount() == 1) {
                    selectedOrder = clientOrderTable.getSelectionModel().getSelectedItem();
                }
            }catch (NullPointerException ignored){}
        });
    }
    public void adminLog(){

    }
    public void storekeeperLog(){
        setVisibility(viewInfoAboutTeacherPage, "storeKeeper");
        setStorekeeperMedicineTable();
    }
    public void pharmacistLog(){
        setVisibility(viewInfoAboutTeacherPage, "pharmacist");
        setPharmacistOrderTable();
        pharmacistOrderTable.setOnMouseClicked(mouseEvent -> {
            try {
                if (mouseEvent.getClickCount() == 1) {
                    selectedOrder = pharmacistOrderTable.getSelectionModel().getSelectedItem();

                }
            }catch (NullPointerException ignored){}
        });
    }
    public void directorLog(){

    }
    public void setStorekeeperMedicineTable(){
        StorekeeperService storekeeperService = new StorekeeperService();
        setCellProperty(storeKeeperTableMap);
        storekeeperMedicineTable.setItems(storekeeperService.readMedicine());
    }
    public void setClientOrderTable(){
        ClientService clientService = new ClientService();
        setCellProperty(clientOrderTableMap);
        clientOrderTable.setItems(clientService.readOrders());
    }
    public void setPharmacistOrderTable(){
        PharmacistService pharmacistService = new PharmacistService();
        setCellProperty(pharmacistOrderTableMap);
        pharmacistOrderTable.setItems(pharmacistService.readOrder());
    }
    public void updateStatus(){
        try {
            Alerts.confirmationAlert();
            PharmacistService pharmacistService = new PharmacistService(this);
            ClientService clientService = new ClientService(this);
            switch (role) {
                case client:
                    clientService. updateOrderStatus();
                    setClientOrderTable();
                    break;
                case pharmacist:
                    pharmacistService.updatePharmacist();
                    setPharmacistOrderTable();
                    break;
            }
        }catch (CancellationException e){
            return;
        }
    }


}
