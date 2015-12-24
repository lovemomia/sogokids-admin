package com.sogokids.user.service;

import com.sogokids.user.model.Func;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/21.
 */
public interface FuncService {
    public Func get(int id);
    public List<Func> getEntityList();
    public int insert(Func entity);
    public int update(Func entity);
    public int delete(int id);

    public Func formEntity(HttpServletRequest req,int id);

    public boolean isFunc(int uid,String link);

    public List<Func> getYList(int r_id);

    public List<Func> getWList(int r_id);
}
