package com.sogokids.course.service;

import com.sogokids.course.model.CourseTeacher;
import com.sogokids.system.model.Teacher;

import java.util.List;

/**
 * Created by hoze on 15/11/20.
 */
public interface CourseTeacherService {

    public List<Teacher> getByCourseId(int course_id);
    public List<CourseTeacher> getCourseTeachers(int course_id);

    public int insert(int course_id,int teacher_id);
    public int delete(int course_id, int teacher_id);

}
