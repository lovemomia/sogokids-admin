package com.sogokids.group.model;

/**
 * Created by hoze on 15/12/25.
 */
public class SelectionGroup {

    private int id;
    private String name;
    private int xkFlag = 0;
    private int status;
    private String addTime;
    private String courseName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXkFlag() {
        return xkFlag;
    }

    public void setXkFlag(int xkFlag) {
        this.xkFlag = xkFlag;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
