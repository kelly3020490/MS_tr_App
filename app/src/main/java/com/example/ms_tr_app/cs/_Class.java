package com.example.ms_tr_app.cs;

import java.io.Serializable;

public class _Class implements Serializable {
    private String cs_num;
    private String t_num_1;
    private String t_num_2;
    private String cs_name;

    public _Class() {
        super();

    }

    public _Class(String cs_num, String t_num_1, String t_num_2, String cs_name) {
        this.cs_num = cs_num;
        this.t_num_1 = t_num_1;
        this.t_num_2 = t_num_2;
        this.cs_name = cs_name;
    }

    public String getCs_num() {
        return cs_num;
    }

    public void setCs_num(String cs_num) {
        this.cs_num = cs_num;
    }

    public String getT_num_1() {
        return t_num_1;
    }

    public void setT_num_1(String t_num_1) {
        this.t_num_1 = t_num_1;
    }

    public String getT_num_2() {
        return t_num_2;
    }

    public void setT_num_2(String t_num_2) {
        this.t_num_2 = t_num_2;
    }

    public String getCs_name() {
        return cs_name;
    }

    public void setCs_name(String cs_name) {
        this.cs_name = cs_name;
    }


}

