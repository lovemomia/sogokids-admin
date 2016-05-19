package com.sogokids.dealGroup.model;

import com.sogokids.query.model.Customer;

/**
 * Created by hoze on 16/4/21.
 */
public class DealGroup {

    private int id;
    private int ownerId;//团长id(创建拼团的用户id)
    private int dealGroupSubjectId;
    private int maxJoinCount;
    private String startTime;
    private String endTime;
    private int status;
    private String addTime;

    private Customer customer;
    private DealGroupSubject dealGroupSubject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDealGroupSubjectId() {
        return dealGroupSubjectId;
    }

    public void setDealGroupSubjectId(int dealGroupSubjectId) {
        this.dealGroupSubjectId = dealGroupSubjectId;
    }

    public int getMaxJoinCount() {
        return maxJoinCount;
    }

    public void setMaxJoinCount(int maxJoinCount) {
        this.maxJoinCount = maxJoinCount;
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

    public DealGroupSubject getDealGroupSubject() {
        return dealGroupSubject;
    }

    public void setDealGroupSubject(DealGroupSubject dealGroupSubject) {
        this.dealGroupSubject = dealGroupSubject;
    }
}