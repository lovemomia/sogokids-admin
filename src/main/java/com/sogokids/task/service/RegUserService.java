package com.sogokids.task.service;

import com.sogokids.task.model.RegUser;

import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/11.
 */
public interface RegUserService {
    public int insert(RegUser entity);
    public List<RegUser> getEntitys();
    public Map getregUsers();
    public RegUser getDateRegUser();
    public int getAllUserCount();

}
