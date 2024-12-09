package com.example.ums.BLL.Service;

import com.example.ums.DAL.Interfaces.*;
import com.example.ums.DAL.Repositories.AdminCRUD.AdminCRUDMongo;
import com.example.ums.DAL.Repositories.AdminCRUD.AdminCRUDSQL;
import com.example.ums.DAL.Repositories.ClientCRUD.ClientCRUDMongo;
import com.example.ums.DAL.Repositories.ClientCRUD.ClientCRUDSQL;
import com.example.ums.DAL.Repositories.DirectorCRUD.DirectorCRUDMongo;
import com.example.ums.DAL.Repositories.DirectorCRUD.DirectorCRUDSQL;
import com.example.ums.DAL.Repositories.LogINBCRUD.LogINCRUDMongo;
import com.example.ums.DAL.Repositories.LogINBCRUD.LogINCRUDSQL;
import com.example.ums.DAL.Repositories.PharmacistCRUD.PharmacistCRUDMongo;
import com.example.ums.DAL.Repositories.PharmacistCRUD.PharmacistCRUDSQL;
import com.example.ums.DAL.Repositories.StorekeeperCRUD.StoreKeeperCRUDMongo;
import com.example.ums.DAL.Repositories.StorekeeperCRUD.StorekeeperCRUDSQL;
import com.example.ums.UI.DBs;

public class DBSelection {
        public <T> T select(Class<T> interfaceType, DBs dbType) {
            if (interfaceType == IAdmin.class) {
                return (T) (dbType == DBs.mongoDB ? new AdminCRUDMongo() : new AdminCRUDSQL());
            } else if (interfaceType == IClient.class) {
                return dbType == DBs.mongoDB ? (T) new ClientCRUDMongo() : (T) new ClientCRUDSQL();
            } else if (interfaceType == IDirector.class) {
                return dbType == DBs.mongoDB ? (T) new DirectorCRUDMongo() : (T) new DirectorCRUDSQL();
            } else if (interfaceType == IPharmacist.class) {
                return dbType == DBs.mongoDB ? (T) new PharmacistCRUDMongo() : (T) new PharmacistCRUDSQL();
            } else if (interfaceType == IStorekeeper.class) {
                return dbType == DBs.mongoDB ? (T) new StoreKeeperCRUDMongo() : (T) new StorekeeperCRUDSQL();
            } else if (interfaceType == ILogIN.class) {
                return dbType == DBs.mongoDB ? (T) new LogINCRUDMongo() : (T) new LogINCRUDSQL();
            } else {
                throw new IllegalArgumentException("Некорректный тип интерфейса: " + interfaceType.getName());
            }
        }


}
