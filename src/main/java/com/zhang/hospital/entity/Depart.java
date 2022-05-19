package com.zhang.hospital.entity;

public class Depart {
    private Integer depart_id;
    private String depart_name;

    public Integer getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Integer depart_id) {
        this.depart_id = depart_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    @Override
    public String toString() {
        return "Depart{" +
                "depart_id=" + depart_id +
                ", depart_name='" + depart_name + '\'' +
                '}';
    }


}
