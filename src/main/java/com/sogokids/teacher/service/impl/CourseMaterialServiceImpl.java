package com.sogokids.teacher.service.impl;

import com.sogokids.teacher.model.CourseMaterial;
import com.sogokids.teacher.service.CourseMaterialService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public CourseMaterial get(int id) {

        String sql = "select Id,CourseId,Content,Status,AddTime from SG_CourseMaterial where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseMaterial entity = new CourseMaterial();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setContent(rs.getString("Content"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(CourseMaterial entity) {
        String sql = "insert into SG_CourseMaterial(CourseId,Content,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getContent(), Quantity.STATUS_ONE};
        int reData = 0;
        reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(CourseMaterial entity) {
        String sql = "update SG_CourseMaterial set CourseId = ?, Content = ?  where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getContent(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseMaterial set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseMaterial formEntity(HttpServletRequest request, int id) {
        CourseMaterial entity = new CourseMaterial();
        entity.setId(id);
        entity.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        if (request.getParameter("content") == null){
            entity.setContent("");
        }else{
            entity.setContent(request.getParameter("content"));
        }

        return entity;
    }

    @Override
    public boolean isMaterial(int courseId){
        boolean reDate = false;
        String sql = "select Id,CourseId,Content,Status,AddTime from SG_CourseMaterial where CourseId = ? and Status > ? ";
        final Object [] params = new Object[]{courseId, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            reDate = true;
        }

        return reDate;
    }

}
