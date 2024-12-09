module com.example.ums {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires mongo.java.driver;








    exports com.example.ums.DAL.Models;
    opens com.example.ums.DAL.Models to javafx.fxml;
    exports com.example.ums.DAL.Interfaces;
    opens com.example.ums.DAL.Interfaces to javafx.fxml;
    exports com.example.ums.DAL.Repositories;
    opens com.example.ums.DAL.Repositories to javafx.fxml;
    exports com.example.ums.UI;
    opens com.example.ums.UI to javafx.fxml;
    exports com.example.ums.DAL.Repositories.AdminCRUD;
    opens com.example.ums.DAL.Repositories.AdminCRUD to javafx.fxml;
    exports com.example.ums.DAL.Repositories.ClientCRUD;
    opens com.example.ums.DAL.Repositories.ClientCRUD to javafx.fxml;
    exports com.example.ums.DAL.Repositories.DirectorCRUD;
    opens com.example.ums.DAL.Repositories.DirectorCRUD to javafx.fxml;
    exports com.example.ums.DAL.Repositories.PharmacistCRUD;
    opens com.example.ums.DAL.Repositories.PharmacistCRUD to javafx.fxml;
    exports com.example.ums.DAL.Repositories.StorekeeperCRUD;
    opens com.example.ums.DAL.Repositories.StorekeeperCRUD to javafx.fxml;
    exports com.example.ums.BLL.DTO;
    opens com.example.ums.BLL.DTO to javafx.fxml;
}