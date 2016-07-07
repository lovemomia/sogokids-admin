package com.sogokids.wd.service;

import com.sogokids.wd.model.Expert;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/6/27.
 */
public interface ExpertService {

    public List<Expert> getEntitys();
    public Expert get(int id);
    public int insert(Expert entity);
    public int update(Expert entity);
    public int delete(int id);
    public Expert formEntity(HttpServletRequest request, int id);
}
