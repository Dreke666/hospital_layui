package com.zhang.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.hospital.dao.DepartDao;
import com.zhang.hospital.entity.Depart;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartServiceImpl implements DepartService{
    @Autowired
    private DepartDao departDao;

    @Override
    public ResultUtil getDepartList(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Depart> departs=departDao.getCatalogList();
        PageInfo<Depart> departPageInfo=new PageInfo<>(departs);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(departPageInfo.getTotal());
        resultUtil.setData(departPageInfo.getList());
        return resultUtil;
    }

    @Override
    public Depart getDepartByDepartName(String depart) {
        return departDao.getDepartByDepartName(depart);
    }

    @Override
    public void addDepart(Depart depart) {
        departDao.addDepart(depart);
    }

    @Override
    public void updateDepart(Depart depart) {
        departDao.updateDepart(depart);
    }

    @Override
    public Depart getDepartById(Integer depart_id) {
        return departDao.getDepartById(depart_id);
    }

    @Override
    public List<Depart> getAllDeparts() {
        return departDao.getAllDeparts();
    }

    @Override
    public void delDepartById(Integer depart_id) {
        departDao.delDepartById(depart_id);
    }
}
