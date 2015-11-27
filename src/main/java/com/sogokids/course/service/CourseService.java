package com.sogokids.course.service;

import com.sogokids.course.model.Course;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface CourseService {
    public List<Course> getEntitys();
    public List<Course> getEntitysById(int id);
    public List<Course> getEntitysBySubId(int id);
    public Course get(int id);
    public int insert(Course entity);
    public int insertKey(Course course);
    public int update(Course entity);
    public int updateStatus(int course_id, int status);
    public int updateNotice(int course_id, String notice);
    public int delete(int id);
    public Course formEntity(HttpServletRequest request, int id);
}
