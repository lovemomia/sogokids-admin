package com.sogokids.system.service;

import com.sogokids.system.model.AppVersion;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/12/21.
 */
public interface AppVersionService {

    public List<AppVersion> getEntitys();
    public AppVersion get(int id);
    public int insert(AppVersion entity);
    public int update(AppVersion entity);
    public int delete(int id);
    public AppVersion formEntity(HttpServletRequest request, int id);

}
