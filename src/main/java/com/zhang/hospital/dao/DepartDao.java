package com.zhang.hospital.dao;

import com.zhang.hospital.entity.Depart;

import java.util.List;

public interface DepartDao {
    List<Depart> getCatalogList();

    Depart getDepartByDepartName(String depart);

    void addDepart(Depart depart);

    void updateDepart(Depart depart);

    Depart getDepartById(Integer depart_id);

    List<Depart> getAllDeparts();

    void delDepartById(Integer depart_id);
}
