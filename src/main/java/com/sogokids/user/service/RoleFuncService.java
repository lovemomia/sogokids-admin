package com.sogokids.user.service;

import com.sogokids.user.model.RoleFunc;

import java.util.List;

/**
 * Created by hoze on 15/10/23.
 */
public interface RoleFuncService {

    public RoleFunc get(int id);
    public List<RoleFunc> getEntityList();
    public List<RoleFunc> getRoleFunc(int roleId);
    public int insert(RoleFunc entity);
    public int update(RoleFunc entity);
    public int delete(RoleFunc entity);

    public RoleFunc formEntity(int funcId,int roleId);
}
