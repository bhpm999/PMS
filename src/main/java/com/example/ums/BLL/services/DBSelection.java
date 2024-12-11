package com.example.ums.BLL.services;

import com.example.ums.DAL.repositories.*;
import com.example.ums.DAL.repositories.impl.mongo.AdminMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.AdminSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.ClientMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.ClientSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.ComboboxMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.ComboboxSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.DirectorMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.DirectorSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.LogINMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.LogINSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.PharmacistMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.PharmacistSQLRepositoryImpl;
import com.example.ums.DAL.repositories.impl.mongo.StoreKeeperMongoRepositoryIml;
import com.example.ums.DAL.repositories.impl.sql.StorekeeperSQLRepositoryImpl;
import com.example.ums.UI.DBs;

public class DBSelection {
        public <T> T select(Class<T> interfaceType, DBs dbType) {
            if (interfaceType == AdminRepository.class) {
                return (T) (dbType == DBs.mongoDB ? new AdminMongoRepositoryIml() : new AdminSQLRepositoryImpl());
            } else if (interfaceType == ClientRepository.class) {
                return dbType == DBs.mongoDB ? (T) new ClientMongoRepositoryIml() : (T) new ClientSQLRepositoryImpl();
            } else if (interfaceType == DirectorRepository.class) {
                return dbType == DBs.mongoDB ? (T) new DirectorMongoRepositoryIml() : (T) new DirectorSQLRepositoryImpl();
            } else if (interfaceType == PharmacistRepository.class) {
                return dbType == DBs.mongoDB ? (T) new PharmacistMongoRepositoryIml() : (T) new PharmacistSQLRepositoryImpl();
            } else if (interfaceType == StorekeeperRepository.class) {
                return dbType == DBs.mongoDB ? (T) new StoreKeeperMongoRepositoryIml() : (T) new StorekeeperSQLRepositoryImpl();
            } else if (interfaceType == LogINRepository.class) {
                return dbType == DBs.mongoDB ? (T) new LogINMongoRepositoryIml() : (T) new LogINSQLRepositoryImpl();
            } else if (interfaceType == ComboboxRepository.class) {
                return dbType == DBs.mongoDB ? (T) new ComboboxMongoRepositoryIml() : (T) new ComboboxSQLRepositoryImpl();
            } else {
                throw new IllegalArgumentException("Некорректный тип интерфейса: " + interfaceType.getName());
            }
        }


}
