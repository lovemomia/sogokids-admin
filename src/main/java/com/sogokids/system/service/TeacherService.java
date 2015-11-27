package com.sogokids.system.service;

import com.sogokids.system.model.Teacher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/11/13.
 */
public interface TeacherService {

    public List<Teacher> getEntitys();
    public List<Teacher> getEntitysByWhere(String where);
    public Teacher get(int id);
    public int insert(Teacher entity);
    public int update(Teacher entity);
    public int delete(int id);
    public Teacher formEntity(HttpServletRequest request, int id);

    public String getY_Teachers(int course_id);
    public String getW_Teachers(int course_id);

}
