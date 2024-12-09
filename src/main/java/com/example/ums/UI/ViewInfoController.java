package com.example.ums.UI;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.OrderDTO;
import com.example.ums.BLL.Service.ClientBLL;
import com.example.ums.BLL.Service.PharmacistBLL;
import com.example.ums.BLL.Service.StorekeeperBLL;
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
        StorekeeperBLL storekeeperBLL = new StorekeeperBLL();
        setCellProperty(storeKeeperTableMap);
        storekeeperMedicineTable.setItems(storekeeperBLL.readMedicine());
    }
    public void setClientOrderTable(){
        ClientBLL clientBLL = new ClientBLL();
        setCellProperty(clientOrderTableMap);
        clientOrderTable.setItems(clientBLL.readOrders());
    }
    public void setPharmacistOrderTable(){
        PharmacistBLL pharmacistBLL = new PharmacistBLL();
        setCellProperty(pharmacistOrderTableMap);
        pharmacistOrderTable.setItems(pharmacistBLL.readOrder());
    }
    public void updateStatus(){
        try {
            Alerts.confirmationAlert();
            PharmacistBLL pharmacistBLL = new PharmacistBLL(this);
            ClientBLL clientBLL = new ClientBLL(this);
            switch (role) {
                case client:
                    clientBLL. updateOrderStatus();
                    setClientOrderTable();
                    break;
                case pharmacist:
                    pharmacistBLL.updatePharmacist();
                    setPharmacistOrderTable();
                    break;
            }
        }catch (CancellationException e){
            return;
        }
    }


}
