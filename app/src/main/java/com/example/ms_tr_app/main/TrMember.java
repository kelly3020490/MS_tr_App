package com.example.ms_tr_app.main;

import java.io.Serializable;

public class TrMember implements Serializable {
    private String t_num;
    private String t_password;

    public TrMember() {
        super();
    }

    public TrMember(String t_num, String t_password) {
        super();
        this.t_num = t_num;
        this.t_password = t_password;
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



}
