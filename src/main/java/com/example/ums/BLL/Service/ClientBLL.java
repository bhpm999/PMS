package com.example.ums.BLL.Service;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.OrderDTO;
import com.example.ums.DAL.Interfaces.IClient;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.example.ums.UI.AddController;
import com.example.ums.UI.ViewInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientBLL extends DBSelection {
    private ViewInfoController viewInfoController;
    private AddController addController;
    public ClientBLL(AddController addController){
        this.addController = addController;
    }
    public ClientBLL(ViewInfoController viewInfoController){
        this.viewInfoController = viewInfoController;
    }
    public ClientBLL(){}
    IClient clientCRUD = select(IClient.class,LogINBLL.db);
    public void updateOrderStatus(){
        ViewInfoController orderInstance = viewInfoController;
        Object id = orderInstance.selectedOrder.getId();
        Map<String, Object> p = Map.of("id",id);
        clientCRUD.update(p);
    }
    public void createOrder() {
        AddController medicineInstance = addController;
        List<MedicineDTO> medicineDTOs = medicineInstance.chosenMedicine;
        List<Medicine> medicines = new ArrayList<>();
        for (MedicineDTO mDTO:
             medicineDTOs) {
            Medicine medicine = new Medicine(mDTO.getGroup(),mDTO.getName(),mDTO.getCount(),mDTO.getCost(),mDTO.getProvider(),mDTO.getState());
            medicines.add(medicine);
        }

        Map<String, Object> p = Map.of(
                "medicines", medicines
        );
        clientCRUD.create(p);
    }
     public ObservableList<MedicineDTO> readMedicines(){
        ObservableList<MedicineDTO> medicineDTOS = FXCollections.observableArrayList();
         for (Medicine m:
              clientCRUD.readMedicine()) {
             MedicineDTO medicineDTO = new MedicineDTO(
                     m.getGroup(), m.getName(), m.getCount(), m.getCost(), m.getProvider(), m.getState()
             );
             medicineDTOS.add(medicineDTO);
         }
         return medicineDTOS;
    }
    public ObservableList<OrderDTO> readOrders(){
        ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList();
        for (Order order:
            clientCRUD.readOrder()) {
            OrderDTO orderDTO = new OrderDTO(
                    order.getName(),order.getProvider(),order.getState(), order.getCount(), order.getCost(),order.getStatus(),order.getId()
            );
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;

    }

}