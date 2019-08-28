package com.example.ms_tr_app.schedule;

public class ScheduleVO {
    private Integer date;
    private String leave;
    private String leave_time;

    private ScheduleVO(){

    }

    public ScheduleVO(Integer date, String leave, String leave_time) {
        this.date = date;
        this.leave = leave;
        this.leave_time = leave_time;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }
}
