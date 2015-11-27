package com.sogokids.course.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseTeacher;
import com.sogokids.course.service.CourseTeacherService;
import com.sogokids.system.model.Teacher;
import com.sogokids.system.service.TeacherService;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/11/20.
 */
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    private TeacherService teacherService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Teacher> getByCourseId(int course_id) {
        List<Teacher> teachers = new ArrayList<Teacher>();
        List<CourseTeacher> courseTeachers = this.getCourseTeachers(course_id);
        if (courseTeachers.size() > 0) {
            for (CourseTeacher courseTeacher:courseTeachers) {
                teachers.add(teacherService.get(courseTeacher.getTeacherId()));
            }
        }

        return teachers;
    }

    @Override
    public List<CourseTeacher> getCourseTeachers(int course_id){
        List<CourseTeacher> courseTeachers = new ArrayList<CourseTeacher>();
        String sql = "select Id,CourseId,TeacherId,Status,AddTime from SG_CourseTeacher where CourseId = ? and Status > ? order by Id desc";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseTeacher entity = new CourseTeacher();
                int id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setTeacherId(Integer.parseInt(list.get(i).get("TeacherId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                courseTeachers.add(entity);
            }
        }

        return courseTeachers;
    }

    @Override
    public int insert(int course_id, int teacher_id) {
        String sql = "insert into SG_CourseTeacher(CourseId,TeacherId,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{course_id, teacher_id, Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int course_id, int teacher_id) {
        String sql = "update SG_CourseTeacher set Status = ? where CourseId = ? and TeacherId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, course_id, teacher_id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }
}
