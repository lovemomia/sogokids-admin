package com.sogokids.home.service.impl;

import com.sogokids.home.model.Banner;
import com.sogokids.home.service.BannerService;
import com.sogokids.system.service.CityService;
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
public class BannerServiceImpl implements BannerService {

    @Autowired
    private CityService cityService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Banner> getEntitys() {
        List<Banner> reData = new ArrayList<Banner>();
        String sql = "select Id,CityId,Cover,Action,Weight,Status,AddTime from SG_Banner where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Banner entity = new Banner();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("CityId").toString()));
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setAction(list.get(i).get("Action").toString());
                entity.setWeight(Integer.parseInt(list.get(i).get("Weight").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setCityName(cityService.get(Integer.parseInt(list.get(i).get("CityId").toString())).getName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Banner get(int id) {

        String sql = "select Id,CityId,Cover,Action,Weight,Status,AddTime from SG_Banner where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Banner entity = new Banner();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCityId(rs.getInt("CityId"));
                entity.setCover(rs.getString("Cover"));
                entity.setAction(rs.getString("Action"));
                entity.setWeight(rs.getInt("Weight"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(Banner entity) {
        String sql = "insert into SG_Banner(CityId,Cover,Action,Weight,Status,AddTime) value(?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCityId(), entity.getCover(), entity.getAction(), entity.getWeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Banner entity) {
        String sql = "update SG_Banner set CityId = ?, Cover = ?, Action = ?, Weight = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getCover(), entity.getAction(), entity.getWeight(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Banner set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Banner formEntity(HttpServletRequest request, int id) {
        Banner entity = new Banner();

        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setCover(request.getParameter("cover"));
        entity.setAction(request.getParameter("action"));
        entity.setWeight(Integer.parseInt(request.getParameter("weight")));

        return entity;
    }
}
