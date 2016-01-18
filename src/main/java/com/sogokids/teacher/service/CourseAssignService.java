package com.sogokids.teacher.service;

import com.sogokids.teacher.model.CourseAssign;

import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/1/7.
 */
public interface CourseAssignService {
    public Map<String,List<CourseAssign>> getCourseAssigns();
    public List<CourseAssign> getTeacherCourses(int teacher_id);
}
