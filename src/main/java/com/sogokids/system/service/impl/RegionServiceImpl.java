package com.sogokids.system.service.impl;

import com.sogokids.system.model.Region;
import com.sogokids.system.service.CityService;
import com.sogokids.system.service.RegionService;
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
 * Created by hoze on 15/7/6.
 */
@Service
public class RegionServiceImpl implements RegionService {

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
    public Region get(int id) {
        String sql = "select id,name,parentId,cityId,status,addTime from SG_Region where id = ? and status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Region entity = new Region();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setParentId(rs.getInt("parentId"));
                entity.setCityId(rs.getInt("cityId"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Region> getEntitys() {
        List<Region> reData = new ArrayList<Region>();
        String sql = "select id,name,parentId,cityId,status,addTime from SG_Region where status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Region entity = new Region();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setParentId(Integer.parseInt(list.get(i).get("parentId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Region entity) {
        String sql = "insert into SG_Region(cityId, name, parentId, status,addTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCityId(), entity.getName(), entity.getParentId(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
     public List<Region> getEntitysByKey(int cityId) {
        List<Region> reData = new ArrayList<Region>();
        String sql = "select id,name,parentId,cityId,status,addTime from SG_Region where cityId = ? and status > ? ";
        Object [] params = new Object[]{cityId,Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Region entity = new Region();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setParentId(Integer.parseInt(list.get(i).get("parentId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Region> getEntitysBycpId(int cityId,int parentId) {
        List<Region> reData = new ArrayList<Region>();
        String sql = "select id,name,parentId,cityId,status,addTime from SG_Region where cityId = ? and parentId = ? and status > ? ";
        Object [] params = new Object[]{cityId,parentId,Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Region entity = new Region();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setParentId(Integer.parseInt(list.get(i).get("parentId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Region> getEntitysByparentId(int parentId) {
        List<Region> reData = new ArrayList<Region>();
        String sql = "select id,name,parentId,cityId,status,addTime from SG_Region where parentId = ? and status > ? ";
        Object [] params = new Object[]{parentId,Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Region entity = new Region();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setParentId(Integer.parseInt(list.get(i).get("parentId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int update(Region entity) {
        String sql = "update SG_Region set cityId = ? , name = ?, parentId = ? where id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getName(), entity.getParentId(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Region set status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Region formEntity(HttpServletRequest request, int id) {
        Region entity = new Region();
        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setParentId(Integer.parseInt(request.getParameter("parentId")));
        entity.setName(request.getParameter("name"));

        return entity;
    }

    @Override
    public List<Region> getEntities(List<Region> regions){
        if(regions.size() > 0){
            for (int i = 0; i < regions.size(); i++) {
                Region entity = regions.get(i);
                int cityId = entity.getCityId();
                if (cityId > 0){
                    entity.setCityname(cityService.get(cityId).getName());
                }
                int pId = entity.getParentId();
                if (pId == 0 ) {
                    entity.setParentname("无所属区域");
                }else {
                    entity.setParentname(this.get(pId).getName());
                }
            }
        }

        return regions;
    }

}
