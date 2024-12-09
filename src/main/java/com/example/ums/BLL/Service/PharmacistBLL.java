package com.example.ums.BLL.Service;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.BLL.DTO.OrderDTO;
import com.example.ums.DAL.Interfaces.IPharmacist;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.DAL.Models.Order;
import com.example.ums.UI.ViewInfoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class PharmacistBLL extends DBSelection {
    private ViewInfoController viewInfoController;
    public PharmacistBLL(ViewInfoController viewInfoController){
        this.viewInfoController = viewInfoController;
    }
    public PharmacistBLL(){}
    private IPharmacist pharmacistCRUD = select(IPharmacist.class,LogINBLL.db);
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
