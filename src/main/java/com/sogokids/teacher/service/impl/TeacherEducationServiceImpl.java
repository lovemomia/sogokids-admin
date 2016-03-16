package com.sogokids.teacher.service.impl;

import com.sogokids.teacher.model.TeacherEducation;
import com.sogokids.teacher.service.TeacherEducationService;
import org.apache.commons.lang3.StringUtils;
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
public class TeacherEducationServiceImpl implements TeacherEducationService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TeacherEducation getTeacherEducation(int userId){
        TeacherEducation teacherEducation = new TeacherEducation();
        String sql = "SELECT Id,UserId,School,Major,Level,Time,Status,AddTime FROM SG_TeacherEducation where Status > 0 and UserId = ? ";
        Object [] params = new Object[]{userId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            int year = 0;
            int year_i = 0;
            for (int i = 0; i < list.size(); i++) {
                if (i == 0){
                    int time = 0;
                    String year_time = list.get(i).get("Time").toString();
                    if (year_time != null && !year_time.equals("") && StringUtils.indexOf(year_time,"-－") > 0){
                        time =  Integer.parseInt(StringUtils.split(year_time, "-－")[0]);
                    }
                    year = time;
                }else{
                    int time = 0;
                    String year_time = list.get(i).get("Time").toString();
                    if (year_time != null && !year_time.equals("") && StringUtils.indexOf(year_time, "-－") > 0){
                        time =  Integer.parseInt(StringUtils.split(year_time,"-－")[0]);
                    }
                    if (time > year){
                        year = time;
                        year_i = i;
                    }
                }
            }
            for (int i = year_i; i < year_i+1; i++) {
                teacherEducation.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                teacherEducation.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                teacherEducation.setSchool(list.get(i).get("School").toString());
                teacherEducation.setMajor(list.get(i).get("Major").toString());
                teacherEducation.setLevel(list.get(i).get("Level").toString());
                teacherEducation.setTime(list.get(i).get("Time").toString());
            }
        }

        return teacherEducation;
    }

    @Override
    public List<TeacherEducation> getTeacherEducations(int userId){
        List<TeacherEducation> teacherEducations = new ArrayList<TeacherEducation>();
        String sql = "SELECT Id,UserId,School,Major,Level,Time,Status,AddTime FROM SG_TeacherEducation where Status > 0 and UserId = ? ";
        Object [] params = new Object[]{userId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        for (int i = 0; i < list.size(); i++) {
            TeacherEducation teacherEducation = new TeacherEducation();
            teacherEducation.setId(Integer.parseInt(list.get(i).get("Id").toString()));
            teacherEducation.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
            teacherEducation.setSchool(list.get(i).get("School").toString());
            teacherEducation.setMajor(list.get(i).get("Major").toString());
            teacherEducation.setLevel(list.get(i).get("Level").toString());
            teacherEducation.setTime(list.get(i).get("Time").toString());

            teacherEducations.add(teacherEducation);
        }

        return teacherEducations;
    }


}
