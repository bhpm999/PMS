package com.example.ums.DAL.repositories;

import java.util.List;

public interface ComboboxRepository {
    List<String> findPosts();
    List<String> findStates();
    List<String> findMedicineGroups();
    List<String> findProviders(String state);
    List<String> findMedicines(String group);
    List<String> findWorkers(String post);
}
