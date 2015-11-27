package com.sogokids.order.model;

import com.sogokids.course.model.Course;
import com.sogokids.subject.model.SubjectSku;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
public class OrderPackage {
    private int id;
    private int orderId;
    private int skuId;
    private BigDecimal price;
    private int courseCount;
    private int bookableCount;
    private int status;
    private String addTime;
    private String updateTime;

    private SubjectSku subjectSku;

    private List<Course> courses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public int getBookableCount() {
        return bookableCount;
    }

    public void setBookableCount(int bookableCount) {
        this.bookableCount = bookableCount;
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

    public SubjectSku getSubjectSku() {
        return subjectSku;
    }

    public void setSubjectSku(SubjectSku subjectSku) {
        this.subjectSku = subjectSku;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
