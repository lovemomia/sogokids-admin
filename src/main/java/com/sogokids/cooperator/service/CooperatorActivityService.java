package com.sogokids.cooperator.service;

import com.sogokids.cooperator.model.CooperatorActivity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/3/31.
 */
public interface CooperatorActivityService {
    public CooperatorActivity get(int id);
    public List<CooperatorActivity> getEntitys();
    public List<CooperatorActivity> getEntitysByCoopId(int coopId);
    public int insert(CooperatorActivity entity);
    public int update(CooperatorActivity entity);
    public int delete(int id);
    public CooperatorActivity formEntity(HttpServletRequest request, int id);
    public CooperatorActivity getActivityEntrys(int id);

    public boolean isUnlockedStock(int id,int stock);
}
