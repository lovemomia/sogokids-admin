package com.sogokids.query.model;

/**
 * Created by hoze on 15/12/10.
 */
public class CourseTimeQuery {

    private int courseId;
    private int subjectId;
    private int skuId;
    private int courseParentId;
    private int skuParentId;
    private int skuCourseId;
    private int skuPlaceId;
    private String courseTitle;
    private String courseCover;
    private String skuStartTime;
    private String skuEndTime;
    private String skuDeadline;

    private int userCount;

    private String placeName;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getCourseParentId() {
        return courseParentId;
    }

    public void setCourseParentId(int courseParentId) {
        this.courseParentId = courseParentId;
    }

    public int getSkuParentId() {
        return skuParentId;
    }

    public void setSkuParentId(int skuParentId) {
        this.skuParentId = skuParentId;
    }

    public int getSkuCourseId() {
        return skuCourseId;
    }

    public void setSkuCourseId(int skuCourseId) {
        this.skuCourseId = skuCourseId;
    }

    public int getSkuPlaceId() {
        return skuPlaceId;
    }

    public void setSkuPlaceId(int skuPlaceId) {
        this.skuPlaceId = skuPlaceId;
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

    public String getSkuDeadline() {
        return skuDeadline;
    }

    public void setSkuDeadline(String skuDeadline) {
        this.skuDeadline = skuDeadline;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
}
