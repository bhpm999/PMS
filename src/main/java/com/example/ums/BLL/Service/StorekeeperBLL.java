package com.example.ums.BLL.Service;

import com.example.ums.BLL.DTO.MedicineDTO;
import com.example.ums.DAL.Interfaces.IStorekeeper;
import com.example.ums.DAL.Models.Medicine;
import com.example.ums.UI.AddController;
import com.example.ums.UI.DeleteController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Map;

public class StorekeeperBLL extends DBSelection {
    private AddController addController;
    private DeleteController deleteController;
    public StorekeeperBLL(AddController addController){
        this.addController = addController;
    }
    public StorekeeperBLL(DeleteController deleteController){
        this.deleteController = deleteController;
    }
    public StorekeeperBLL(){}
    IStorekeeper storekeeperCRUD = select(IStorekeeper.class,LogINBLL.db);
    public void deleteMedicine() {
        DeleteController medicineInstance = deleteController;
        String medicine = medicineInstance.storekeeperChooseMedicine.getValue();

        Map<String, Object> params = Map.of("medicine", medicine);
        storekeeperCRUD.delete(params);
    }
    public void createMedicine() {
        AddController medicineInstance = addController;
        String medicineName = medicineInstance.storekeeperMedicineField.getText();
        int count = Integer.parseInt(medicineInstance.storekeeperCountField.getText());
        double price = Double.parseDouble(medicineInstance.storekeeperCostField.getText());
        String state = medicineInstance.storekeeperChooseState.getValue();
        String medicineGroup = medicineInstance.storekeeperChooseMedicineGroup.getValue();
        String provider = medicineInstance.storekeeperChooseProvider.getValue();

        Map<String, Object> params = Map.of(
                "medicineName", medicineName,
                "count", count,
                "price", price,
                "state", state,
                "medicineGroup", medicineGroup,
                "provider", provider
        );
        storekeeperCRUD.create(params);
    }
    public ObservableList<MedicineDTO> readMedicine(){
        ObservableList<MedicineDTO> medicineDTOS = FXCollections.observableArrayList();
        for (Medicine m:
                storekeeperCRUD.readMedicine()) {
            MedicineDTO medicineDTO = new MedicineDTO(
                    m.getGroup(), m.getName(), m.getCount(), m.getCost(), m.getProvider(), m.getState()
            );
            medicineDTOS.add(medicineDTO);
        }
        return medicineDTOS;
    }


}
