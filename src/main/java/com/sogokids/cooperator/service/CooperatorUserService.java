package com.sogokids.cooperator.service;

import com.sogokids.cooperator.model.CooperatorUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/3/31.
 */
public interface CooperatorUserService {

    public CooperatorUser get(int id);
    public List<CooperatorUser> getEntitys();
    public int insert(CooperatorUser entity);
    public int update(CooperatorUser entity);
    public int delete(int id);
    public CooperatorUser formEntity(HttpServletRequest request, int id);
    public CooperatorUser login(String name, String pwd);
    public int verifyName(String name);
}
