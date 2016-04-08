package com.sogokids.cooperator.service.impl;

import com.sogokids.cooperator.model.Cooperator;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.lang3.StringUtils;
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
 * Created by hoze on 16/3/31.
 */
@Service
public class CooperatorServiceImpl implements CooperatorService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Cooperator get(int id) {
        String sql = "select Id, Name, Cover, Status, AddTime from SG_Cooperator where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Cooperator entity = new Cooperator();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setName(rs.getString("Name"));
                entity.setCover(rs.getString("Cover"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Cooperator> getEntitys() {
        List<Cooperator> reData = new ArrayList<Cooperator>();
        String sql = "select Id, Name, Cover, Status, AddTime from SG_Cooperator where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            try {
                for (int i = 0; i < list.size(); i++) {
                    Cooperator entity = new Cooperator();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setName(list.get(i).get("Name").toString());
                    entity.setCover(list.get(i).get("Cover") == null ? "" : list.get(i).get("Cover").toString());
                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime") == null ? "" : list.get(i).get("AddTime").toString());
                    reData.add(entity);
                }
            }catch (Exception _ex){
                _ex.printStackTrace();
            }
        }

        return reData;
    }

    @Override
    public int insert(Cooperator entity) {
        String sql = "insert into SG_Cooperator(Name, Cover, Status, AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getName(), entity.getCover(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Cooperator entity) {
        String sql = "update SG_Cooperator set Name = ?, Cover = ? where Id = ? ";
        Object [] params = new Object[]{entity.getName(), entity.getCover(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Cooperator set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Cooperator formEntity(HttpServletRequest request, int id) {
        Cooperator entity = new Cooperator();

        entity.setId(id);
        entity.setName(StringUtils.isEmpty(request.getParameter("name")) ? "" : request.getParameter("name"));
        entity.setCover(StringUtils.isEmpty(request.getParameter("cover")) ? "" : request.getParameter("cover"));

        return entity;
    }
}
