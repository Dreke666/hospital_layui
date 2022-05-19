package com.zhang.hospital.dao;

import com.zhang.hospital.entity.Reservation;

import java.util.List;

public interface ReservationDao {
    void addReservation(Reservation reservation);

    List<Reservation> getAllMyReservationList(Integer user_id);

    Reservation getResByResId(int res_id);

    void updateRes(Reservation reservation);

    List<Reservation> getDoctorResList(Integer doctor_id);

    List<Reservation> getDoctorResDoneList(Integer doctor_id);
}
