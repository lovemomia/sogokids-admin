package com.sogokids.system.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hoze on 15/12/30.
 */
public interface MenuService {
    public String getMenuHtml(HttpServletRequest req, int uid);
}
