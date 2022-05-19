package com.zhang.hospital.service;

import com.zhang.hospital.entity.Depart;
import com.zhang.hospital.util.ResultUtil;

import java.util.List;

public interface DepartService {
    ResultUtil getDepartList(Integer page, Integer limit);

    Depart getDepartByDepartName(String depart);

    void addDepart(Depart depart);

    void updateDepart(Depart depart);

    Depart getDepartById(Integer depart_id);

    List<Depart> getAllDeparts();

    void delDepartById(Integer depart_id);
}
