package com.sogokids.order.model;

import com.sogokids.query.model.Customer;
import com.sogokids.subject.model.Subject;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
public class Order {
    private int id;
    private int userId;
    private int subjectId;
    private String contact;
    private String mobile;
    private String inviteCode;
    private int status;
    private String addTime;
    private String updateTime;

    private Customer customer;
    private Subject subject;
    private Payment payment;
    private List<OrderPackage> orderPackages;
    private BigDecimal priceSum;

    private int tk_mark;

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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<OrderPackage> getOrderPackages() {
        return orderPackages;
    }

    public void setOrderPackages(List<OrderPackage> orderPackages) {
        this.orderPackages = orderPackages;
    }

    public BigDecimal getPriceSum() {
        return priceSum;
    }

    public void setPriceSum(BigDecimal priceSum) {
        this.priceSum = priceSum;
    }

    public int getTk_mark() {
        return tk_mark;
    }

    public void setTk_mark(int tk_mark) {
        this.tk_mark = tk_mark;
    }
}
