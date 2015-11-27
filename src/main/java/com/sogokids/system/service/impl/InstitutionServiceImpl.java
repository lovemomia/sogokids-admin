package com.sogokids.system.service.impl;

import com.sogokids.system.model.Institution;
import com.sogokids.system.service.InstitutionService;
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
 * Created by hoze on 15/10/20.
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Institution> getEntitys() {
        List<Institution> reData = new ArrayList<Institution>();
        String sql = "select Id,Name,Cover,Intro,Status,AddTime from SG_Institution where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = null;
        try {
            list = jdbcTemplate.queryForList(sql, params);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Institution entity = new Institution();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setName(list.get(i).get("Name").toString());
                    entity.setCover(list.get(i).get("Cover").toString());
                    entity.setIntro(list.get(i).get("Intro").toString());
                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime").toString());
                    reData.add(entity);
                }
            }
        }catch (Exception _ex){
            _ex.printStackTrace();
        }

        return reData;
    }

    @Override
    public Institution get(int id) {
        String sql = "select Id,Name,Cover,Intro,Status,AddTime from SG_Institution where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Institution entity = new Institution();
        try {
            jdbcTemplate.query(sql, params, new RowCallbackHandler() {
                public void processRow(ResultSet rs) throws SQLException {
                    entity.setId(rs.getInt("Id"));
                    entity.setName(rs.getString("Name"));
                    entity.setCover(rs.getString("Cover"));
                    entity.setIntro(rs.getString("Intro"));
                    entity.setStatus(rs.getInt("Status"));
                    entity.setAddTime(rs.getString("AddTime"));
                }
            });
        }catch (Exception _ex){
            _ex.printStackTrace();
        }

        return entity;
    }

    @Override
    public int insert(Institution entity) {
        String sql = "insert into SG_Institution(Name,Cover,Intro,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getName(), entity.getCover(), entity.getIntro(), Quantity.STATUS_ONE};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(Institution entity) {
        String sql = "update SG_Institution set Name = ?, Cover = ?,Intro = ? where Id = ? ";
        Object [] params = new Object[]{entity.getName(), entity.getCover(), entity.getIntro(), entity.getId()};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Institution set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Institution formEntity(HttpServletRequest request, int id) {
        Institution entity = new Institution();
        try {
            entity.setId(id);
            entity.setName(request.getParameter("name"));
            entity.setCover(request.getParameter("cover"));
            entity.setIntro(request.getParameter("intro"));
        }catch (Exception _ex){
            _ex.printStackTrace();
        }

        return entity;
    }
}
