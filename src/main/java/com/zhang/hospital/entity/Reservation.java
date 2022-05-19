package com.zhang.hospital.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Reservation {
    private Integer res_id;
    private Integer status; //0表示未预定状态 1 表示预约状态 2 接诊状态 3 接诊完成

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date order_time;//预约时间

    private Date create_time;// 创建预约时间
    private Integer res_user_id;
    private Integer res_doc_id;

    private Doctor doctor;


    private User user;

    public Integer getRes_id() {
        return res_id;
    }

    public void setRes_id(Integer res_id) {
        this.res_id = res_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getRes_user_id() {
        return res_user_id;
    }

    public void setRes_user_id(Integer res_user_id) {
        this.res_user_id = res_user_id;
    }

    public Integer getRes_doc_id() {
        return res_doc_id;
    }

    public void setRes_doc_id(Integer res_doc_id) {
        this.res_doc_id = res_doc_id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "res_id=" + res_id +
                ", status=" + status +
                ", order_time=" + order_time +
                ", create_time=" + create_time +
                ", res_user_id=" + res_user_id +
                ", res_doc_id=" + res_doc_id +
                ", doctor=" + doctor +
                ", user=" + user +
                '}';
    }
}
