package com.example.ms_tr_app.main;

import java.io.Serializable;

public class TrMember implements Serializable {
    private String t_num;
    private String t_password;
    private String t_name;
    private int t_photo;
private String t_job;

    public TrMember() {
        super();
    }

    public TrMember(String t_num, String t_password, String t_name,String t_job,int t_photo) {
        super();
        this.t_num = t_num;
        this.t_password = t_password;
        this.t_name = t_name;
        this.t_job = t_job;
        this.t_photo = t_photo;
    }

    public String getT_num() {
        return t_num;
    }

    public void setT_num(String t_num) {
        this.t_num = t_num;
    }

    public String getT_password() {
        return t_password;
    }

    public void setT_password(String t_password) {
        this.t_password = t_password;
    }

    public String getT_name() {
        return t_name;
    }

    public int getT_photo() {
        return t_photo;
    }

    public void setT_photo(int t_photo) {
        this.t_photo = t_photo;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;

    }

    public String getT_job() {
        return t_job;
    }

    public void setT_job(String t_job) {
        this.t_job = t_job;
    }
}
