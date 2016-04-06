package com.sogokids.cooperator.service;

import com.sogokids.cooperator.model.Cooperator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/3/31.
 */
public interface CooperatorService {

    public Cooperator get(int id);
    public List<Cooperator> getEntitys();
    public int insert(Cooperator entity);
    public int update(Cooperator entity);
    public int delete(int id);
    public Cooperator formEntity(HttpServletRequest request, int id);

}
