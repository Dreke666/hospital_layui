package com.zhang.hospital.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
//病历
public class Note {

    private Integer note_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    private String realname;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String note_title;
    private String note_content;
    private Integer user_id;
    private Integer doctor_id;






    public Integer getNote_id() {
        return note_id;
    }

    public void setNote_id(Integer note_id) {
        this.note_id = note_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }


}
