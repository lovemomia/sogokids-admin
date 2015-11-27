package com.sogokids.system.service.impl;

import com.sogokids.system.model.City;
import com.sogokids.system.service.CityService;
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
 * Created by hoze on 15/6/25.
 */
@Service
public class CityServiceImpl implements CityService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<City> getEntitys() {
        List<City> reData = new ArrayList<City>();
        String sql = "select id,name,status,addTime from SG_City where status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                City entity = new City();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public City get(int id) {

        String sql = "select id,name,status,addTime from SG_City where id = ? and status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final City entity = new City();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(City entity) {
        String sql = "insert into SG_City(name, status,addTime) value(?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getName(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(City entity) {
        String sql = "update SG_City set name = ? where id = ? ";
        Object [] params = new Object[]{entity.getName(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_City set status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public City formEntity(HttpServletRequest request, int id) {
        City entity = new City();
        entity.setId(id);
        entity.setName(request.getParameter("name"));
        return entity;
    }
}
