package com.example.ums.BLL.services;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.OrderDTO;
import com.example.ums.DAL.repositories.PharmacistRepository;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.example.ums.UI.controllers.ViewInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class PharmacistService extends DBSelection {
    private ViewInfoController viewInfoController;
    public PharmacistService(ViewInfoController viewInfoController){
        this.viewInfoController = viewInfoController;
    }
    public PharmacistService(){}
    private PharmacistRepository pharmacistCRUD = select(PharmacistRepository.class, LogINService.db);
    public void updatePharmacist() {
        ViewInfoController orderInstance = viewInfoController;
        Object id = orderInstance.selectedOrder.getId();

        Map<String, Object> params = Map.of("id", id);
        pharmacistCRUD.update(params);
    }
    public ObservableList<MedicineDTO> readMedicine(){
        ObservableList<MedicineDTO> medicineDTOS = FXCollections.observableArrayList();
        for (Medicine m:
                pharmacistCRUD.readMedicines()) {
            MedicineDTO medicineDTO = new MedicineDTO(
                    m.getGroup(), m.getName(), m.getCount(), m.getCost(), m.getProvider(), m.getState()
            );
            medicineDTOS.add(medicineDTO);
        }
        return medicineDTOS;
    }

    public ObservableList<OrderDTO> readOrder(){
        ObservableList<OrderDTO> orderDTOS = FXCollections.observableArrayList();
        for (Order order:
                pharmacistCRUD.readOrders()) {
            OrderDTO orderDTO = new OrderDTO(
                    order.getName(),order.getProvider(),order.getState(), order.getCount(), order.getCost(),order.getStatus(),order.getId()
            );
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

}
