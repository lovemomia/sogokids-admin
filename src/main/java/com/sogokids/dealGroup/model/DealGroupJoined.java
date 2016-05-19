package com.sogokids.dealGroup.model;

import com.sogokids.query.model.Customer;

/**
 * Created by hoze on 16/4/21.
 */
public class DealGroupJoined {
    private int id;
    private int dealGroupId;
    private int userId;
    private int refundId;//返现状态(0未返现，大于0已返现)
    private int status;
    private String addTime;

    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDealGroupId() {
        return dealGroupId;
    }

    public void setDealGroupId(int dealGroupId) {
        this.dealGroupId = dealGroupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRefundId() {
        return refundId;
    }

    public void setRefundId(int refundId) {
        this.refundId = refundId;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
