package com.sogokids.dealGroup.service;

import com.sogokids.dealGroup.model.DealGroupSubject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/4/21.
 */
public interface DealGroupSubjectService {

    public List<DealGroupSubject> getEntitys();
    public DealGroupSubject get(int id);
    public int insert(DealGroupSubject entity);
    public int update(DealGroupSubject entity);
    public int delete(int id);
    public DealGroupSubject formEntity(HttpServletRequest request, int id);
    public String getSubjectSkuJsonStr(int deal_id);
    public int updateStatus(int id,int mark);
}
