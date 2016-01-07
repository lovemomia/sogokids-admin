package com.sogokids.system.service.impl;

import cn.momia.common.config.Configuration;
import com.sogokids.system.model.Teacher;
import com.sogokids.system.service.TeacherService;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by hoze on 15/11/13.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private Configuration configuration;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Teacher> getEntitys() {
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Teacher entity = new Teacher();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setAvatar(list.get(i).get("Avatar").toString());
                if (list.get(i).get("Education") == null){
                    entity.setEducation("");
                }else{
                    entity.setEducation(list.get(i).get("Education").toString());
                }
                if (list.get(i).get("Experience") == null){
                    entity.setExperience("");
                }else{
                    entity.setExperience(list.get(i).get("Experience").toString());
                }

                entity.setSex(Integer.parseInt(list.get(i).get("Sex").toString()));
                entity.setJob(Integer.parseInt(list.get(i).get("Job").toString()));
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Teacher> getEntitysByWhere(String where) {
        String whereStr = " where Status > ? ";
        whereStr = whereStr + where;
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher " + whereStr;
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Teacher entity = new Teacher();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setAvatar(list.get(i).get("Avatar").toString());
                entity.setEducation(list.get(i).get("Education").toString());
                if (list.get(i).get("Experience") == null){
                    entity.setExperience("");
                }else{
                    entity.setExperience(list.get(i).get("Experience").toString());
                }
                entity.setSex(Integer.parseInt(list.get(i).get("Sex").toString()));
                entity.setJob(Integer.parseInt(list.get(i).get("Job").toString()));
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Teacher get(int id) {
        String sql = "select Id,UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Teacher entity = new Teacher();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setName(rs.getString("Name"));
                entity.setAvatar(rs.getString("Avatar"));
                entity.setEducation(rs.getString("Education"));
                entity.setExperience(rs.getString("Experience"));
                entity.setSex(rs.getInt("Sex"));
                entity.setJob(rs.getInt("Job"));
                entity.setMobile(rs.getString("Mobile"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(Teacher entity) {
        String sql = "insert into SG_Teacher(UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getAvatar(), entity.getEducation(), entity.getExperience(), entity.getSex(), entity.getJob(), entity.getMobile(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Teacher entity) {
        String sql = "update SG_Teacher set UserId = ?, Name = ?, Avatar = ?, Education = ?, Experience = ?, Sex = ?, Job = ?, Mobile = ?  where Id = ? ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getAvatar(), entity.getEducation(), entity.getExperience(), entity.getSex(), entity.getJob(), entity.getMobile(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Teacher set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Teacher formEntity(HttpServletRequest request, int id) {
        Teacher entity = new Teacher();

        entity.setId(id);
        entity.setUserId(Integer.parseInt(request.getParameter("userId")));
        entity.setName(request.getParameter("name"));
        entity.setAvatar(request.getParameter("cover"));
        entity.setEducation(request.getParameter("education"));
        entity.setExperience(request.getParameter("experience"));
        entity.setSex(Integer.parseInt(request.getParameter("sex")));
        entity.setJob(Integer.parseInt(request.getParameter("job")));
        entity.setMobile(request.getParameter("mobile"));

        return entity;
    }

    @Override
    public String getY_Teachers(int course_id) {
        StringBuffer sb = new StringBuffer();
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Status > ? and Id in (SELECT TeacherId FROM sogokids.SG_CourseTeacher where Status > ? and CourseId = ?) ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, course_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        sb.append(getHtmlTableStrat1());
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                sb.append("<tr>");
                sb.append("<td>");
                    sb.append("<input type='checkbox' id='y_teacher' name='y_teacher' value='"+list.get(i).get("Id")+"'>");
                sb.append("</td>");
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + list.get(i).get("Avatar");
                sb.append("<td>");
                    sb.append("<img class='teacher_w_h' src='" + url + "' />").append("&numsp;&numsp;");
                    sb.append(list.get(i).get("Name"));
                sb.append("</td>");
                sb.append("</tr>");
            }
        }
        sb.append(getHtmlTableEnd());
        return sb.toString();
    }

    @Override
    public String getW_Teachers(int course_id) {
        StringBuffer sb = new StringBuffer();
        List<Teacher> reData = new ArrayList<Teacher>();
        String sql = "select Id,UserId,Name,Avatar,Education,Experience,Sex,Job,Mobile,Status,AddTime from SG_Teacher where Status > ? and Id not in (SELECT TeacherId FROM sogokids.SG_CourseTeacher where Status > ? and CourseId = ?) ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, course_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        sb.append(getHtmlTableStrat());
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                sb.append("<tr>");
                sb.append("<td>");
                sb.append("<input type='checkbox' id='w_teacher' name='w_teacher' value='"+list.get(i).get("Id")+"'>");
                sb.append("</td>");
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + list.get(i).get("Avatar");
                sb.append("<td>");
                sb.append("<img class='teacher_w_h' src='" + url + "' />").append("&numsp;&numsp;");
                sb.append(list.get(i).get("Name"));
                sb.append("</td>");
                sb.append("</tr>");
            }
        }
        sb.append(getHtmlTableEnd());

        return sb.toString();
    }

    private String getHtmlTableStrat(){
        StringBuffer sb = new StringBuffer();
        sb.append("<table class='table table-striped table-bordered table-hover dataTables-example'>");
        sb.append("<thead>");
        sb.append("<tr class='gradeX'>");
        sb.append("<th>勾选</th>");
        sb.append("<th>讲师</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        return sb.toString();
    }

    private String getHtmlTableStrat1(){
        StringBuffer sb = new StringBuffer();
        sb.append("<table class='table table-striped table-bordered table-hover'>");
        sb.append("<thead>");
        sb.append("<tr class='gradeX'>");
        sb.append("<th>勾选</th>");
        sb.append("<th>讲师</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        return sb.toString();
    }

    private String getHtmlTableEnd(){
        StringBuffer sb = new StringBuffer();
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }
}
