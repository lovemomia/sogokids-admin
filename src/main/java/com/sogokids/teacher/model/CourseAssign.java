package com.sogokids.teacher.model;

/**
 * Created by hoze on 16/1/7.
 */
public class CourseAssign {
    private int courseId;
    private int skuId;
    private int placeId;
    private String courseTitle;
    private String skuStartTime;
    private String skuEndTime;
    private String placeAddress;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getSkuStartTime() {
        return skuStartTime;
    }

    public void setSkuStartTime(String skuStartTime) {
        this.skuStartTime = skuStartTime;
    }

    public String getSkuEndTime() {
        return skuEndTime;
    }

    public void setSkuEndTime(String skuEndTime) {
        this.skuEndTime = skuEndTime;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

}
