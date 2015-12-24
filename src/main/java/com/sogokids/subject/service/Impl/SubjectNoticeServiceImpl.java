package com.sogokids.subject.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.sogokids.subject.model.SubjectImg;
import com.sogokids.subject.model.SubjectNotice;
import com.sogokids.subject.service.SubjectNoticeService;
import com.sogokids.utils.entity.Notice;
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
 * Created by hoze on 15/10/14.
 */
@Service
public class SubjectNoticeServiceImpl implements SubjectNoticeService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SubjectNotice> getEntitys(int subId) {
        List<SubjectNotice> reData = new ArrayList<SubjectNotice>();
        String sql = "select Id,Title,Content,SubjectId from SG_SubjectNotice WHERE SubjectId = ? ";
        Object [] params = new Object[]{subId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SubjectNotice entity = new SubjectNotice();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setContent(list.get(i).get("Content").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public SubjectNotice get(int id) {
        String sql = "select Id,Title,Content,SubjectId from SG_SubjectNotice where Id = ? ";
        final Object [] params = new Object[]{id};
        final SubjectNotice entity = new SubjectNotice();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setTitle(rs.getString("Title"));
                entity.setContent(rs.getString("Content"));
            }
        });

        return entity;
    }

    @Override
    public int insert(SubjectNotice entity) {
        String sql = "insert into SG_SubjectNotice(Title,Content,SubjectId) value(?, ?, ?) ";
        Object [] params = new Object[]{entity.getTitle(), entity.getContent(), entity.getSubjectId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(SubjectNotice entity) {
        String sql = "update SG_SubjectNotice set Title = ?, Content = ?, SubjectId = ? where Id = ? ";
        Object [] params = new Object[]{entity.getTitle(), entity.getContent(), entity.getSubjectId(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = " delete from SG_SubjectNotice where Id = ? ";
        Object [] params = new Object[]{id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public SubjectNotice formEntity(HttpServletRequest request,int subId, int id) {
        SubjectNotice entity = new SubjectNotice();

        entity.setId(id);
        entity.setSubjectId(subId);
        entity.setTitle(request.getParameter("n_title"));
        entity.setContent(request.getParameter("n_content"));

        return entity;
    }

    @Override
    public String getContents(int sub_id){
        List<SubjectNotice> sub_notices = this.getEntitys(sub_id);
        List<Notice> notices = new ArrayList<Notice>();
        if (sub_notices.size() > 0){
            for (SubjectNotice sub_notice : sub_notices){
                Notice entity = new  Notice();
                entity.setTitle(sub_notice.getTitle());
                entity.setContent(sub_notice.getContent());

                notices.add(entity);
            }

        }

        return JSONObject.toJSONString(notices);
    }

    @Override
    public String getContentsHtml(int sub_id){
        StringBuffer sb = new StringBuffer();
        String p_start = "<p>";
        String p_end = "</p>";
        List<SubjectNotice> sub_notices = this.getEntitys(sub_id);
        if (sub_notices.size() > 0){
            for (SubjectNotice sub_notice : sub_notices){
                sb.append(p_start);
                    sb.append(sub_notice.getTitle()+"<br>");
                    sb.append(sub_notice.getContent()+"<br>");
                    sb.append("<br>");
                    String btn_value = "'"+sub_notice.getId()+"'";
                    sb.append("<a class='btn btn-success btn-sm' href='javascript:void(0)' onclick=\"noticeEdit(" + btn_value + ")\"><i class='fa fa-pencil'></i>编辑</a>");
                    sb.append("&nbsp;&nbsp;");
                    sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"noticeDel(" + btn_value + ")\"><i class='fa fa-trash'></i>删除</a>");
                sb.append(p_end);
                sb.append("<div class='hr-line-dashed'></div>");
            }
        }

        return sb.toString();
    }
}
