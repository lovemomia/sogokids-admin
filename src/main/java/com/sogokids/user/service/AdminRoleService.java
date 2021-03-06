package com.sogokids.user.service;

import com.sogokids.user.model.AdminRole;

import java.util.List;

/**
 * Created by hoze on 15/10/23.
 */
public interface AdminRoleService {

    public AdminRole get(int id);
    public List<AdminRole> getEntityList();
    public List<AdminRole> getAdminRole(int adminId);
    public int insert(AdminRole entity);
    public int update(AdminRole entity);
    public int delete(AdminRole entity);

    public AdminRole formEntity(int adminId,int roleId);
}
