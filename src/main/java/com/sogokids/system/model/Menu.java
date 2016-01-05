package com.sogokids.system.model;

/**
 * Created by hoze on 15/12/30.
 */
public class Menu {

    private int id;
    private String url;
    private String name;
    private int active;
    private int parentId;
    private int type;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Menu(int id, String url, String name, int active, int parentId, int type){
        this.id = id;
        this.url = url;
        this.name = name;
        this.active = active;
        this.parentId = parentId;
        this.type = type;
    }
}
