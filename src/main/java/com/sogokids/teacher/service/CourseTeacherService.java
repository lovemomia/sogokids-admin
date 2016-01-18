package com.sogokids.teacher.service;

import com.sogokids.teacher.model.CourseTeacher;
import com.sogokids.teacher.model.Teacher;

import java.util.List;

/**
 * Created by hoze on 15/11/20.
 */
public interface CourseTeacherService {

    public List<Teacher> getByCourseId(int course_id);
    public List<CourseTeacher> getCourseTeachers(int course_id);
    public List<CourseTeacher> getCourseTeachersBySkuId(int course_id,int sku_id);

    public int insert(int course_id, int course_sku_id, int user_id, int teacher_id);
    public int delete(int course_id, int course_sku_id, int teacher_id);

}
