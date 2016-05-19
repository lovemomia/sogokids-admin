package com.sogokids.dealGroup.model;

/**
 * Created by hoze on 16/4/21.
 */
public class DealGroupSubject {
    private int id;
    private int subjectId;
    private int subjectSkuId;
    private String title;
    private int maxJoinCount;
    private int days;
    private int status;
    private String addTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSubjectSkuId() {
        return subjectSkuId;
    }

    public void setSubjectSkuId(int subjectSkuId) {
        this.subjectSkuId = subjectSkuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxJoinCount() {
        return maxJoinCount;
    }

    public void setMaxJoinCount(int maxJoinCount) {
        this.maxJoinCount = maxJoinCount;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
