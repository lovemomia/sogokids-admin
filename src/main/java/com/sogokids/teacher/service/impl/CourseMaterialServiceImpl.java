package com.sogokids.teacher.service.impl;

import com.sogokids.teacher.model.CourseMaterial;
import com.sogokids.teacher.service.CourseMaterialService;
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
public class CourseMaterialServiceImpl implements CourseMaterialService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CourseMaterial> getMaterials(){
        List<CourseMaterial> courseMaterials = new ArrayList<CourseMaterial>();
        String sql = "SELECT a.Id,b.Id as courseId,b.Title,b.Cover,c.Id as subId,c.Title as subTitle FROM SG_CourseMaterial a,SG_Course b,SG_Subject c where a.Status > 0 and b.Status > 0 and b.Status != 3 and c.Status > 0 and c.Status != 3 and a.CourseId = b.Id and b.SubjectId = c.Id order by a.AddTime desc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseMaterial entity = new CourseMaterial();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int courseId = Integer.parseInt(list.get(i).get("courseId").toString());
                entity.setCourseId(courseId);
                entity.setCourseTitle(list.get(i).get("Title").toString());
                entity.setCourseCover(list.get(i).get("Cover").toString());
                int subjectId = Integer.parseInt(list.get(i).get("subId").toString());
                entity.setSubjectId(subjectId);
                entity.setSubjectTitle(list.get(i).get("subTitle").toString());

                courseMaterials.add(entity);
            }
        }

        return courseMaterials;
    }
}
