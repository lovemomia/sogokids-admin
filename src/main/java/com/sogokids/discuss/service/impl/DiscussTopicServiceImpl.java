package com.sogokids.discuss.service.impl;

import com.sogokids.discuss.model.DiscussTopic;
import com.sogokids.discuss.service.DiscussTopicService;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by hoze on 16/2/23.
 */
@Service
public class DiscussTopicServiceImpl implements DiscussTopicService {
    private final Logger log = LoggerFactory.getLogger(DiscussTopicServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<DiscussTopic> getEntitys() {
        List<DiscussTopic> reData = new ArrayList<DiscussTopic>();
        String sql = "select Id,CityId,SubjectId,Cover,Title,SubTitle,Content,Status,AddTime from SG_DiscussTopic where Status > ? order by status ASC ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                DiscussTopic entity = new DiscussTopic();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("CityId").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setSubTitle(list.get(i).get("SubTitle").toString());
                entity.setContent(list.get(i).get("Content").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public DiscussTopic get(int id) {
        String sql = "select Id,CityId,SubjectId,Cover,Title,SubTitle,Content,Status,AddTime from SG_DiscussTopic where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final DiscussTopic entity = new DiscussTopic();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCityId(rs.getInt("CityId"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setCover(rs.getString("Cover"));
                entity.setTitle(rs.getString("Title"));
                entity.setSubTitle(rs.getString("SubTitle"));
                entity.setContent(rs.getString("Content"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(DiscussTopic entity) {
        String sql = "insert into SG_DiscussTopic(CityId,SubjectId,Cover,Title,SubTitle,Content,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCityId(), entity.getSubjectId(), entity.getCover(), entity.getTitle(), entity.getSubTitle(), entity.getContent(), Quantity.STATUS_TWO};
        int reData = 0;
        try{
            log.info("开始添加话题信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束添加话题信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(DiscussTopic entity) {
        String sql = "update SG_DiscussTopic set CityId = ?, SubjectId = ?, Cover = ?, Title = ?, SubTitle = ?, Content = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getSubjectId(), entity.getCover(), entity.getTitle(), entity.getSubTitle(), entity.getContent(), entity.getId()};
        int reData = 0;
        try{
            log.info("开始修改话题信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束修改话题信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "update SG_DiscussTopic set Status = ? where Id = ? ";
        Object [] params = new Object[]{status,id};
        log.info("开始更新话题状态信息!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束更新话题状态信息!");
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_DiscussTopic set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        log.info("开始删除话题信息!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束删除话题信息!");
        return reData;
    }

    @Override
    public DiscussTopic formEntity(HttpServletRequest request, int id) {
        DiscussTopic entity = new DiscussTopic();

        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
        entity.setCover(request.getParameter("cover"));
        entity.setTitle(request.getParameter("title"));
        entity.setSubTitle(request.getParameter("subTitle"));
        entity.setContent(request.getParameter("content"));

        return entity;
    }
}
