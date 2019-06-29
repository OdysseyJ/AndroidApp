package com.example.bottomnavigation;

public class RewardData {

    private String name;
    private String sec;
    private String mil;
    public RewardData(String sec, String mil){
        this.sec = sec;
        this.mil = mil;
    }
    public String getSec() {
        return sec;
    }

    public void setSec(String title) {
        this.sec = title;
    }

    public String getMil() {
        return mil;
    }

    public void setMil(String content) {
        this.mil = content;
    }
}
