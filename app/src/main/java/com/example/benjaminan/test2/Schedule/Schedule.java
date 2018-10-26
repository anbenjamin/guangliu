package com.example.benjaminan.test2.Schedule;

import java.io.Serializable;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private int sid;
    private String schedule_title;
    private String schedule_content;
    private int hour_start;
    private int minute_start;
    private int hour_finish;
    private int minute_finish;
    private int year_start;
    private int month_start;
    private int day_start;
    private int year_finish;
    private int month_finish;
    private int day_finish;
    private int isImportant;
    private int isUrgent;
    public int getSid(){
        return sid;
    }
    public void setSid(int id){
        this.sid=id;
    }
    public int getHour_start(){
        return hour_start;
    }
    public void setHour_start(int Houre_Start){
        this.hour_start=Houre_Start;
    }
    public int getMinute_start(){
        return minute_start;
    }
    public void setMinute_start(int Minute_Start){
        this.minute_start=Minute_Start;
    }
    public int getHour_finish(){
        return hour_finish;
    }
    public void setHour_finish(int Hour_finish){
        this.hour_finish=Hour_finish;
    }
    public int getMinute_finish(){
        return minute_finish;
    }
    public void setMinute_finish(int Minute_finish){
        this.minute_finish=Minute_finish;
    }
    public int getYear_start(){
        return year_start;
    }
    public void setYear_start(int Year_start){
        this.year_start=Year_start;
    }
    public int getMonth_start(){
        return month_start;
    }
    public void setMonth_start(int Month_start){
        this.month_start=Month_start;
    }
    public int getDay_start(){
        return day_start;
    }
    public void setDay_start(int Day_start){
        this.day_start=Day_start;
    }
    public int getYear_finish(){
        return year_finish;
    }
    public void setYear_finish(int Year_finish){
        this.year_finish=Year_finish;
    }
    public int getMonth_finish(){
        return month_finish;
    }
    public void setMonth_finish(int Month_finish){
        this.month_finish=Month_finish;
    }
    public int getDay_finish(){
        return day_finish;
    }
    public void setDay_finish(int Day_finish){
        this.day_finish=Day_finish;
    }
    public int getIsImportant(){
        return isImportant;
    }
    public void setIsImportant(int IsImportant){
        this.isImportant=IsImportant;
    }
    public int getIsUrgent(){
        return isUrgent;
    }
    public void setIsUrgent(int IsUrgent){
        this.isUrgent=IsUrgent;
    }
    public String getSchedule_title(){
        return schedule_title;
    }
    public void setSchedule_title(String Schedule_title){
        this.schedule_title=Schedule_title;
    }
    public String getSchedule_content(){
        return schedule_content;
    }
    public void setSchedule_content(String Schedule_content){
        this.schedule_content=Schedule_content;
    }
}
