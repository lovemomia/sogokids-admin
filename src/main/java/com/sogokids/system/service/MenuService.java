package com.sogokids.system.service;

import com.sogokids.system.model.Menu;

import java.util.List;

/**
 * Created by hoze on 15/12/30.
 */
public interface MenuService {
    public List<Menu> getMenus();
    public String getMenuStrings(int uid,int int_x);
}
