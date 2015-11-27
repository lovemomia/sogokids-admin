package com.sogokids.user.service;

import com.sogokids.user.model.AdminRole;

import javax.servlet.http.HttpServletRequest;
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
    public int delete(int id);

    public AdminRole formEntity(int adminId,int roleId);
}
