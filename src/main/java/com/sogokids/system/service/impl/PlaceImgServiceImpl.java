package com.sogokids.system.service.impl;


import com.sogokids.system.model.PlaceImg;
import com.sogokids.system.service.PlaceImgService;
import com.sogokids.images.model.Images;
import com.sogokids.utils.util.Quantity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */
@Service
public class PlaceImgServiceImpl implements PlaceImgService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PlaceImg get(int id) {

        String sql = "select id,placeId,url,width,height,status,addTime from SG_PlaceImg where id = ? and status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final PlaceImg entity = new PlaceImg();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setPlaceId(rs.getInt("placeId"));
                entity.setUrl(rs.getString("url"));
                entity.setWidth(rs.getInt("width"));
                entity.setHeight(rs.getInt("height"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<PlaceImg> getEntitys() {
        List<PlaceImg> reData = new ArrayList<PlaceImg>();
        String sql = "select id,placeId,url,width,height,status,addTime from SG_PlaceImg where status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                PlaceImg entity = new PlaceImg();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("placeId").toString()));
                entity.setUrl(list.get(i).get("url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;

    }

    @Override
    public List<PlaceImg> getEntitysByKey(int placeId) {
        List<PlaceImg> reData = new ArrayList<PlaceImg>();
        String sql = "select id,placeId,url,width,height,status,addTime from SG_PlaceImg where placeId = ? and status > ? ";
        Object [] params = new Object[]{placeId, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                PlaceImg entity = new PlaceImg();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("placeId").toString()));
                entity.setUrl(list.get(i).get("url").toString());
                entity.setWidth(Integer.parseInt(list.get(i).get("width").toString()));
                entity.setHeight(Integer.parseInt(list.get(i).get("height").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(PlaceImg entity) {
        String sql = "insert into SG_PlaceImg(placeId, url, width, height, status,addTime) value(?, ?, ?, ?, ?,NOW()) ";
        Object [] params = new Object[]{entity.getPlaceId(), entity.getUrl(), entity.getWidth(), entity.getHeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(PlaceImg entity) {
        String sql = "update SG_PlaceImg set url = ? , placeId = ?, width = ?, height = ? where id = ? ";
        Object [] params = new Object[]{entity.getUrl(), entity.getPlaceId(), entity.getWidth(), entity.getHeight(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_PlaceImg set status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public PlaceImg formEntity(Images img, int id) {
        PlaceImg entity = new PlaceImg();
        entity.setPlaceId(id);
        entity.setUrl(img.getUrl());
        entity.setWidth(img.getWidth());
        entity.setHeight(img.getHeigth());
        return entity;
    }
}
