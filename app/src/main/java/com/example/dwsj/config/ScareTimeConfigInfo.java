package com.example.dwsj.config;
/*
Created by xiaoyu on 2020/11/26

Describe: 每个场次的抢购时间安排信息

*/


import com.example.dwsj.fragment.anima.AnimaFragment;

import java.util.HashMap;

public class ScareTimeConfigInfo {


    public int number;//场次编号，由上往下，第一场  第二场.....
    public String starTtime;//开始时间
    public String endTime;//结束时间
    public boolean isStartScare;//默认没开始抢
    public boolean isEndScare;//是否结束抢购
    public AnimaFragment.ScareStaue staue;//当前状态  STOP  ING  UNSTART
    public String timeExpend;



    public ScareTimeConfigInfo(int number, String starTtime, String endTime, boolean isStartScare, boolean isEndScare) {
        this.number = number;
        this.starTtime = starTtime;
        this.endTime = endTime;
        this.isStartScare = isStartScare;
        this.isEndScare = isEndScare;
    }

    public String getTimeExpend() {
        return timeExpend;
    }

    public void setTimeExpend(String timeExpend) {
        this.timeExpend = timeExpend;
    }


    public AnimaFragment.ScareStaue getStaue() {
        return staue;
    }

    public void setStaue(AnimaFragment.ScareStaue staue) {
        this.staue = staue;
    }

    public boolean getStartScare() {
        return isStartScare;
    }

    public void setStartScare(boolean startScare) {
        isStartScare = startScare;
    }

    public boolean getEndScare() {
        return isEndScare;
    }

    public void setEndScare(boolean endScare) {
        isEndScare = endScare;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStarTtime() {
        return starTtime;
    }

    public void setStarTtime(String starTtime) {
        this.starTtime = starTtime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
