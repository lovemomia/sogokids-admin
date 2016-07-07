package com.sogokids.wd.service;

import com.sogokids.wd.model.ExpertCourse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/6/27.
 */
public interface ExpertCourseService {

    public List<ExpertCourse> getEntitys();
    public ExpertCourse get(int id);
    public int insert(ExpertCourse entity);
    public int update(ExpertCourse entity);
    public int delete(int id);
    public ExpertCourse formEntity(HttpServletRequest request, int id);
}
