package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.entity.ContentBody;
import cn.momia.admin.web.entity.Images;
import cn.momia.admin.web.service.ContentBodyService;
import cn.momia.admin.web.service.ImagesService;
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
 * Created by hoze on 15/7/23.
 */
@Service
public class ContentBodyServiceImpl implements ContentBodyService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private ImagesService imagesService;

    @Override
    public ContentBody get(int id) {
        String sql = "select id,text,label,link,img,type,contentId,status,addTime from t_content_body where id = ? and status > ? ";
        final Object [] params = new Object[]{id, FinalUtil.DEL_STATUS};
        final ContentBody entity = new ContentBody();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setContentId(rs.getInt("contentId"));
                entity.setText(rs.getString("text"));
                entity.setLabel(rs.getString("label"));
                entity.setLink(rs.getString("link"));
                entity.setImg(rs.getString("img"));
                entity.setType(rs.getInt("type"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<ContentBody> getEntitys() {
        List<ContentBody> reData = new ArrayList<ContentBody>();
        String sql = "select id,text,label,link,img,type,contentId,status,addTime from t_content_body where status > ? order by id desc";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ContentBody entity = new ContentBody();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setContentId(Integer.parseInt(list.get(i).get("contentId").toString()));
                entity.setText(list.get(i).get("text").toString());
                entity.setLabel(list.get(i).get("label").toString());
                entity.setLink(list.get(i).get("link").toString());
                entity.setImg(list.get(i).get("img").toString());
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<ContentBody> getEntitysByContentId(int contentId,String sort) {
        List<ContentBody> reData = new ArrayList<ContentBody>();
        String sql = "select id,text,label,link,img,type,contentId,status,addTime from t_content_body where contentId = ? and status > ? order by id " + sort;
        Object [] params = new Object[]{contentId,FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ContentBody entity = new ContentBody();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setContentId(Integer.parseInt(list.get(i).get("contentId").toString()));
                if (list.get(i).get("text") != null ){
                    entity.setText(list.get(i).get("text").toString());
                }else{
                    entity.setText("");
                }
                if (list.get(i).get("label") != null ){
                    entity.setLabel(list.get(i).get("label").toString());
                }else{
                    entity.setLabel("");
                }
                if (list.get(i).get("link") != null ){
                    entity.setLink(list.get(i).get("link").toString());
                }else{
                    entity.setLink("");
                }
                if (list.get(i).get("img") != null ){
                    entity.setImg(list.get(i).get("img").toString());
                }else{
                    entity.setImg("");
                }
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(ContentBody entity) {
        String sql = "insert into t_content_body(text,label,link,img,type,contentId,status,addTime) value(?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getText(), entity.getLabel(), entity.getLink(), entity.getImg(), entity.getType(), entity.getContentId(), FinalUtil.ADD_STATUS};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(ContentBody entity) {
        String sql = "update t_content_body set text = ?, label = ?, link = ?, img = ?, type = ?, contentId = ? where id = ? ";
        Object [] params = new Object[]{entity.getText(), entity.getLabel(), entity.getLink(), entity.getImg(), entity.getType(), entity.getContentId(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update t_content_body set status = ? where id = ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public ContentBody formEntity(HttpServletRequest request, int id) {
        ContentBody entity = new ContentBody();
        entity.setId(id);
        entity.setContentId(Integer.parseInt(request.getParameter("contentId")));
        entity.setText(request.getParameter("text"));
        entity.setLabel(request.getParameter("label"));
        entity.setLink(request.getParameter("link"));
        entity.setType(Integer.parseInt(request.getParameter("type")));
        entity.setImg(request.getParameter("img"));

        return entity;
    }
    @Override
    public ContentBody formEntity2(HttpServletRequest request) {
        ContentBody entity = new ContentBody();
        entity.setContentId(Integer.parseInt(request.getParameter("contentId")));
        entity.setText(request.getParameter("text"));
        entity.setLabel(request.getParameter("label"));
        entity.setLink(request.getParameter("link"));
        int type = Integer.parseInt(request.getParameter("type"));
        entity.setType(type);
        if (type == 3) {
            Images images = imagesService.uploadImg(request,1,"img");
            entity.setImg(images.getUrl());
        }else{
            entity.setImg("");
        }

        return entity;
    }
}
