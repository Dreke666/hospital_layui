package com.zhang.hospital.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
//公告
public class Promotion
{
    private Integer promotion_id;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pub_date;

    public Integer getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(Integer promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPub_date() {
        return pub_date;
    }

    public void setPub_date(Date pub_date) {
        this.pub_date = pub_date;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotion_id=" + promotion_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pub_date=" + pub_date +
                '}';
    }
}
