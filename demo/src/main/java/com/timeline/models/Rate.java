package com.timeline.models;

public class Rate {
    private int id;
    private int userId;
    private int timelineId;
    private int rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(int timelineId) {
        this.timelineId = timelineId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        if(rate<1){
            System.out.println("Rate value must be greater than 0");
            this.rate=0;
            System.out.println("Rate set 0");
        } else if (rate > 5) {
            System.out.println("Rate value must be less than or equal to 5");
            this.rate=0;
            System.out.println("Rate set 0");
        }else{
            this.rate = rate;
        }

    }
}
