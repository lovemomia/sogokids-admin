package com.sogokids.user.service;

import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/21.
 */
public interface RoleService {
    public Role get(int id);
    public List<Role> getEntityList();
    public int insert(Role entity);
    public int update(Role entity);
    public int delete(int id);

    public Role formEntity(HttpServletRequest req,int id);

    public List<Role> getYList(int uid);
    public List<Role> getWList(int uid);

}
