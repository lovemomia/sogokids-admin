package com.sogokids.home.service;

import com.sogokids.home.model.Recommend;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/5/20.
 */
public interface RecommendService {

    public List<Recommend> getEntitys();
    public Recommend get(int id);
    public int insert(Recommend entity);
    public int update(Recommend entity);
    public int delete(int id);
    public Recommend formEntity(HttpServletRequest request, int id);

}
