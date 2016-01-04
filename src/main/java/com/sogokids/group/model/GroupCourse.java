package com.sogokids.group.model;

/**
 * Created by hoze on 15/12/29.
 */
public class GroupCourse {
    private int id;
    private int groupId;
    private int courseId;
    private int courseSkuId;

    private int status;
    private String addTime;

    private String courseTitle;
    private String courseCover;
    private String courseSkuPlace;
    private String courseSkuStartTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseSkuId() {
        return courseSkuId;
    }

    public void setCourseSkuId(int courseSkuId) {
        this.courseSkuId = courseSkuId;
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

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseCover() {
        return courseCover;
    }

    public void setCourseCover(String courseCover) {
        this.courseCover = courseCover;
    }

    public String getCourseSkuPlace() {
        return courseSkuPlace;
    }

    public void setCourseSkuPlace(String courseSkuPlace) {
        this.courseSkuPlace = courseSkuPlace;
    }

    public String getCourseSkuStartTime() {
        return courseSkuStartTime;
    }

    public void setCourseSkuStartTime(String courseSkuStartTime) {
        this.courseSkuStartTime = courseSkuStartTime;
    }
}
