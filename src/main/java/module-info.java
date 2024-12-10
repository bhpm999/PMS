module com.example.ums {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires mongo.java.driver;








    exports com.example.ums.DAL.Models;
    opens com.example.ums.DAL.Models to javafx.fxml;
    exports com.example.ums.DAL.repositories;
    opens com.example.ums.DAL.repositories to javafx.fxml;
    exports com.example.ums.UI;
    opens com.example.ums.UI to javafx.fxml;
    exports com.example.ums.DAL.repositories.impl.AdminImpl;
    opens com.example.ums.DAL.repositories.impl.AdminImpl to javafx.fxml;
    exports com.example.ums.DAL.repositories.impl.ClientImpl;
    opens com.example.ums.DAL.repositories.impl.ClientImpl to javafx.fxml;
    exports com.example.ums.DAL.repositories.impl.DirectorImpl;
    opens com.example.ums.DAL.repositories.impl.DirectorImpl to javafx.fxml;
    exports com.example.ums.DAL.repositories.impl.PharmacistImpl;
    opens com.example.ums.DAL.repositories.impl.PharmacistImpl to javafx.fxml;
    exports com.example.ums.DAL.repositories.impl.StorekeeperImpl;
    opens com.example.ums.DAL.repositories.impl.StorekeeperImpl to javafx.fxml;
    exports com.example.ums.BLL.DTO;
    opens com.example.ums.BLL.DTO to javafx.fxml;
    exports com.example.ums.BLL.services;
    opens com.example.ums.BLL.services to javafx.fxml;
    exports com.example.ums.DAL.db;
    opens com.example.ums.DAL.db to javafx.fxml;
    exports com.example.ums.UI.controllers;
    opens com.example.ums.UI.controllers to javafx.fxml;
}