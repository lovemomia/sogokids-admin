package com.sogokids.cooperator.model;

/**
 * Created by hoze on 16/3/31.
 */
public class CooperatorUser {

    private int id;
    private int cooperatorId;
    private String name;
    private String password;
    private int status;
    private String addTime;
    private Cooperator cooperator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCooperatorId() {
        return cooperatorId;
    }

    public void setCooperatorId(int cooperatorId) {
        this.cooperatorId = cooperatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Cooperator getCooperator() {
        return cooperator;
    }

    public void setCooperator(Cooperator cooperator) {
        this.cooperator = cooperator;
    }
}
