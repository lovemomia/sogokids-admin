package com.sogokids.cooperator.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hoze on 16/3/31.
 */
public class CooperatorActivity {
    private int id;
    private String cover;
    private String title;
    private String desc;
    private String message;
    private int needPay;
    private BigDecimal price;
    private int cooperatorId;
    private String startTime;
    private String endTime;
    private String onlineTime;
    private String offlineTime;
    private int status;
    private String addTime;

    private Cooperator cooperator;
    private int sum_count;

    private List<ActivityEntry> activityEntries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCooperatorId() {
        return cooperatorId;
    }

    public void setCooperatorId(int cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
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

    public List<ActivityEntry> getActivityEntries() {
        return activityEntries;
    }

    public void setActivityEntries(List<ActivityEntry> activityEntries) {
        this.activityEntries = activityEntries;
    }

    public Cooperator getCooperator() {
        return cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }

    public int getSum_count() {
        return sum_count;
    }

    public void setSum_count(int sum_count) {
        this.sum_count = sum_count;
    }
}
