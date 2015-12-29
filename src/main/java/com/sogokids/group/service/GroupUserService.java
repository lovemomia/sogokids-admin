package com.sogokids.group.service;

import com.sogokids.group.model.GroupUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/12/25.
 */
public interface GroupUserService {
    public List<GroupUser> getEntitys();
    public GroupUser get(int id);
    public GroupUser isget(int gid, int uid);
    public int insert(GroupUser entity);
    public int update(GroupUser entity);
    public int delete(int id);
    public GroupUser formEntity(HttpServletRequest request, int id);
}
