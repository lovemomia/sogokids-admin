package com.sogokids.home.service;

import com.sogokids.home.model.Icon;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface IconService {
    public List<Icon> getEntitys();
    public Icon get(int id);
    public int insert(Icon entity);
    public int update(Icon entity);
    public int delete(int id);
    public Icon formEntity(HttpServletRequest request, int id);
}
