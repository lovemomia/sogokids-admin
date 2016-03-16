package com.sogokids.teacher.service.impl;

import com.sogokids.teacher.model.TeacherExperience;
import com.sogokids.teacher.service.TeacherExperienceService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/1/8.
 */
@Service
public class TeacherExperienceServiceImpl implements TeacherExperienceService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TeacherExperience> getTeacherExperiences(int userId){
        List<TeacherExperience> teacherExperiences = new ArrayList<TeacherExperience>();
        String sql = "SELECT Id,UserId,School,Post,Time,Content,Status,AddTime FROM SG_TeacherExperience where Status > 0 and UserId = ? ";
        Object [] params = new Object[]{userId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        for (int i = 0; i < list.size(); i++) {
            TeacherExperience teacherExperience = new TeacherExperience();
            teacherExperience.setId(Integer.parseInt(list.get(i).get("Id").toString()));
            teacherExperience.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
            teacherExperience.setSchool(list.get(i).get("School").toString());
            teacherExperience.setPost(list.get(i).get("Post").toString());
            teacherExperience.setTime(list.get(i).get("Time").toString());
            teacherExperience.setContent(list.get(i).get("Content").toString());

            teacherExperiences.add(teacherExperience);
        }

        return teacherExperiences;
    }
}
