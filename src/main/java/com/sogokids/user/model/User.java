package com.sogokids.user.model;

/**
 * Created by hoze on 15/8/25.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private int status;
    private String addTime;

    private String menuHtml;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMenuHtml() {
        return menuHtml;
    }

    public void setMenuHtml(String menuHtml) {
        this.menuHtml = menuHtml;
    }
}
