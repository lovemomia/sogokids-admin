package com.sogokids.wd.service.impl;

import com.sogokids.utils.util.Quantity;
import com.sogokids.wd.model.Expert;
import com.sogokids.wd.model.ExpertBanner;
import com.sogokids.wd.model.ExpertCourse;
import com.sogokids.wd.service.ExpertCourseService;
import com.sogokids.wd.service.ExpertService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/6/27.
 */
@Service
public class ExpertCourseServiceImpl implements ExpertCourseService {

    @Autowired
    private ExpertService expertService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ExpertCourse> getEntitys() {
        List<ExpertCourse> reData = new ArrayList<ExpertCourse>();
        String sql = "select Id,ExpertId,Title,Cover,Content,`Desc`,Subhead,Price,StartTime,EndTime,IsClose,Mins,Count,Status,AddTime from SG_ExpertCourse where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ExpertCourse entity = new ExpertCourse();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setExpertId(Integer.parseInt(list.get(i).get("ExpertId").toString()));
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setContent(list.get(i).get("Content").toString());
                entity.setDesc(list.get(i).get("Desc").toString());

                entity.setSubHead(list.get(i).get("Subhead").toString());
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setStartTime(list.get(i).get("StartTime").toString());
                entity.setEndTime(list.get(i).get("EndTime").toString());

                entity.setIsClose(Integer.parseInt(list.get(i).get("IsClose").toString()));
                entity.setMins(Integer.parseInt(list.get(i).get("Mins").toString()));
                entity.setCount(Integer.parseInt(list.get(i).get("Count").toString()));

                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setExpertName(expertService.get(Integer.parseInt(list.get(i).get("ExpertId").toString())).getName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public ExpertCourse get(int id) {
        String sql = "select Id,ExpertId,Title,Cover,Content,`Desc`,Subhead,Price,StartTime,EndTime,IsClose,Mins,Count,Status,AddTime from SG_ExpertCourse where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final ExpertCourse entity = new ExpertCourse();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setExpertId(rs.getInt("ExpertId"));
                entity.setTitle(rs.getString("Title"));
                entity.setCover(rs.getString("Cover"));
                entity.setContent(rs.getString("Content"));
                entity.setDesc(rs.getString("Desc"));
                entity.setSubHead(rs.getString("Subhead"));
                entity.setPrice(rs.getBigDecimal("Price"));

                entity.setStartTime(rs.getString("StartTime"));
                entity.setEndTime(rs.getString("EndTime"));
                entity.setIsClose(rs.getInt("IsClose"));
                entity.setMins(rs.getInt("Mins"));
                entity.setCount(rs.getInt("Count"));

                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(ExpertCourse entity) {
        String sql = "insert into SG_ExpertCourse(ExpertId,Title,Cover,Content,`Desc`,Subhead,Price,StartTime,EndTime,Mins,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getExpertId(), entity.getTitle(), entity.getCover(), entity.getContent(), entity.getDesc(), entity.getSubHead(), entity.getPrice(), entity.getStartTime(), entity.getEndTime(), entity.getMins(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(ExpertCourse entity) {
        String sql = "update SG_ExpertCourse set ExpertId = ?, Title = ?, Cover = ?, Content = ?, `Desc` = ?, Subhead = ?, Price = ?, StartTime = ?, EndTime = ?, Mins = ? where Id = ? ";
        Object [] params = new Object[]{entity.getExpertId(), entity.getTitle(), entity.getCover(), entity.getContent(), entity.getDesc(), entity.getSubHead(), entity.getPrice(), entity.getStartTime(), entity.getEndTime(), entity.getMins(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_ExpertCourse set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public ExpertCourse formEntity(HttpServletRequest request, int id) {
        ExpertCourse entity = new ExpertCourse();

        entity.setId(id);
        entity.setExpertId(Integer.parseInt(request.getParameter("expertId")));
        entity.setTitle(request.getParameter("title"));
        entity.setCover(request.getParameter("cover"));
        entity.setContent(request.getParameter("content"));
        entity.setDesc(request.getParameter("desc"));
        entity.setSubHead(request.getParameter("subhead"));
        BigDecimal price = request.getParameter("price") == null ? new BigDecimal(0):new BigDecimal(request.getParameter("price"));
        entity.setPrice(price);

        entity.setStartTime(request.getParameter("startTime"));
        entity.setEndTime(request.getParameter("endTime"));

        entity.setMins(Integer.parseInt(request.getParameter("mins")));

        return entity;
    }
}
