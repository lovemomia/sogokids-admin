package com.sogokids.subject.service.Impl;

import cn.momia.common.config.Configuration;
import com.sogokids.images.model.Images;
import com.sogokids.subject.model.SubjectImg;
import com.sogokids.subject.service.SubjectImgService;
import com.sogokids.user.service.UserService;
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
 * Created by hoze on 15/10/12.
 */
@Service
public class SubjectImgServiceImpl implements SubjectImgService {

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
    public List<SubjectImg> getEntitys() {
        List<SubjectImg> reData = new ArrayList<SubjectImg>();
        String sql = "select Id,SubjectId,Url,Width,Height,Status,AddTime from SG_SubjectImg where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SubjectImg entity = new SubjectImg();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setUrl(list.get(i).get("Url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("Width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("Height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<SubjectImg> getModelsBySub_Key(int subjectId) {
        List<SubjectImg> reData = new ArrayList<SubjectImg>();
        String sql = "select Id,SubjectId,Url,Width,Height,Status,AddTime from SG_SubjectImg where SubjectId = ? and status > ? ";
        Object [] params = new Object[]{subjectId, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SubjectImg entity = new SubjectImg();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setUrl(list.get(i).get("Url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("Width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("Height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public SubjectImg get(int id) {
        String sql = "select Id,SubjectId,Url,Width,Height,Status,AddTime from SG_SubjectImg where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final SubjectImg entity = new SubjectImg();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setUrl(rs.getString("Url"));
                entity.setWidth(rs.getInt("Width"));
                entity.setHeight(rs.getInt("Height"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(SubjectImg entity) {
        String sql = "insert into SG_SubjectImg(SubjectId,Url,Width,Height,Status,AddTime) value(?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(SubjectImg entity) {
        String sql = "update SG_SubjectImg set SubjectId = ?, Url = ?, Width = ?, Height = ? where Id = ? ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_SubjectImg set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public SubjectImg formEntity(HttpServletRequest request, int id) {
        SubjectImg entity = new SubjectImg();

        entity.setId(id);
        entity.setSubjectId(Integer.parseInt(request.getParameter("SubjectId")));
        entity.setUrl(request.getParameter("Url"));
        entity.setWidth(Integer.parseInt(request.getParameter("Width")));
        entity.setHeight(Integer.parseInt(request.getParameter("Height")));

        return entity;
    }

    @Override
    public SubjectImg formEntity(Images img, int id) {
        SubjectImg entity = new SubjectImg();
        entity.setSubjectId(id);
        entity.setUrl(img.getUrl());
        entity.setWidth(img.getWidth());
        entity.setHeight(img.getHeigth());

        return entity;
    }

    /**
     * 根据活动拼接图片的html字符串
     * @param sub_id
     * @return
     */
    @Override
    public  String getImgHtml(int sub_id){
        StringBuffer sb = new StringBuffer();
        List<SubjectImg> p_img = this.getModelsBySub_Key(sub_id);
        if (p_img.size() > 0){
            for (int i = 0; i < p_img.size(); i++) {
//                String altstr = p_img.get(i).getWidth() + "x" + p_img.get(i).getHeight();
                String url = configuration.getString(Quantity.DISPLAY_IMAGE) + p_img.get(i).getUrl();

                sb.append("<div class='div_img'>");
                sb.append("<ul>");
                sb.append("<li>");
                sb.append("<img class='img_w_h' src='" + url + "' />");
                sb.append("</li>");

                sb.append("<li>");
                sb.append("<a href='javascript:void(0)' class='btn btn-white btn-sm' onclick='delimg("+p_img.get(i).getId()+")'><i class='fa fa-times-circle'></i>删除</a>");
                sb.append("</li>");
                sb.append("</ul>");
                sb.append("</div>");
            }
        }
        return sb.toString();
    }
}
