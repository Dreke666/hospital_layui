package com.zhang.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.hospital.dao.ReservationDao;
import com.zhang.hospital.entity.Reservation;
import com.zhang.hospital.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void addReservation(Reservation reservation) {
        reservationDao.addReservation(reservation);
    }

    @Override
    public ResultUtil getAllMyReservationList(Integer page, Integer limit,Integer user_id) {
        PageHelper.startPage(page,limit);
        List<Reservation> reservations=reservationDao.getAllMyReservationList(user_id);
        PageInfo<Reservation> pageInfo=new PageInfo<>(reservations);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(pageInfo.getTotal());
        resultUtil.setData(pageInfo.getList());
        return resultUtil;
    }

    @Override
    public Reservation getResByResId(int res_id) {
        return reservationDao.getResByResId(res_id);
    }

    @Override
    public void updateRes(Reservation reservation) {
        reservationDao.updateRes(reservation);
    }

    //医生查看患者预约列表
    @Override
    public ResultUtil getDoctorResList(Integer page, Integer limit, Integer doctor_id) {
        PageHelper.startPage(page,limit);
        List<Reservation> reservations=reservationDao.getDoctorResList(doctor_id);
        PageInfo<Reservation> pageInfo=new PageInfo<>(reservations);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(pageInfo.getTotal());
        resultUtil.setData(pageInfo.getList());

        return resultUtil;
    }

    //医生查看已接诊列表
    @Override
    public ResultUtil getDoctorResDoneList(Integer page, Integer limit, Integer doctor_id) {
        PageHelper.startPage(page,limit);
        List<Reservation> reservations=reservationDao.getDoctorResDoneList(doctor_id);
        PageInfo<Reservation> pageInfo=new PageInfo<>(reservations);
        ResultUtil resultUtil=new ResultUtil();
        resultUtil.setCode(0);
        resultUtil.setCount(pageInfo.getTotal());
        resultUtil.setData(pageInfo.getList());

        return resultUtil;
    }

}
