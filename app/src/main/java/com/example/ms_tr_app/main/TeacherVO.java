package com.example.ms_tr_app.main;

import java.io.Serializable;

public class TeacherVO implements Serializable {

    private String t_num;
    private String t_name;
    private String t_gender;
    private String t_id;
    private String t_email;
    private String t_r_address;
    private String t_address;
    private String t_job;
    private String t_password;
    private java.sql.Date t_birthday;
    private byte[] t_photo;
    private Integer t_status;


    public TeacherVO() {
        super();
    }

    public String getT_num() {
        return t_num;
    }
    public void setT_num(String t_num) {
        this.t_num = t_num;
    }
    public String getT_name() {
        return t_name;
    }
    public void setT_name(String t_name) {
        this.t_name = t_name;
    }
    public String getT_gender() {
        return t_gender;
    }
    public void setT_gender(String t_gender) {
        this.t_gender = t_gender;
    }
    public String getT_id() {
        return t_id;
    }
    public void setT_id(String t_id) {
        this.t_id = t_id;
    }
    public String getT_email() {
        return t_email;
    }
    public void setT_email(String t_email) {
        this.t_email = t_email;
    }
    public String getT_r_address() {
        return t_r_address;
    }
    public void setT_r_address(String t_r_address) {
        this.t_r_address = t_r_address;
    }
    public String getT_address() {
        return t_address;
    }
    public void setT_address(String t_address) {
        this.t_address = t_address;
    }
    public String getT_job() {
        return t_job;
    }
    public void setT_job(String t_job) {
        this.t_job = t_job;
    }
    public String getT_password() {
        return t_password;
    }
    public void setT_password(String t_password) {
        this.t_password = t_password;
    }
    public java.sql.Date getT_birthday() {
        return t_birthday;
    }
    public void setT_birthday(java.sql.Date t_birthday) {
        this.t_birthday = t_birthday;
    }
    public byte[] getT_photo() {
        return t_photo;
    }
    public void setT_photo(byte[] t_photo) {
        this.t_photo = t_photo;
    }
    public Integer getT_status() {
        return t_status;
    }
    public void setT_status(Integer t_status) {
        this.t_status = t_status;
    }
}

