package com.sogokids.system.model;

import java.util.List;

/**
 * Created by hoze on 15/12/30.
 */
public class Menu {

    private int id;
    private String MenuCode;
    private String MenuName;
    private String MenuUrl;
    private int MenuActive;
    private int parentId;
    private int sequence;
    private String icon;

    private List<Menu> menus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

    public String getMenuUrl() {
        return MenuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        MenuUrl = menuUrl;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public int getMenuActive() {
        return MenuActive;
    }

    public void setMenuActive(int menuActive) {
        MenuActive = menuActive;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
