package com.sogokids.system.service.impl;


import com.alibaba.fastjson.JSON;
import com.sogokids.system.model.City;
import com.sogokids.system.model.Place;
import com.sogokids.system.model.Region;
import com.sogokids.system.service.CityService;
import com.sogokids.system.service.PlaceService;
import com.sogokids.system.service.RegionService;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private CityService cityService;

    @Autowired
    private RegionService regionService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Place get(int id) {

        String sql = "select id,cityId,regionId,name,address,`desc`,lng,lat,route,status,addTime from SG_Place where id = ? and status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Place entity = new Place();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCityId(rs.getInt("cityId"));
                entity.setRegionId(rs.getInt("regionId"));
                entity.setName(rs.getString("name"));
                entity.setAddress(rs.getString("address"));
                entity.setDesc(rs.getString("desc"));
                entity.setLng(rs.getFloat("lng"));
                entity.setLat(rs.getFloat("lat"));
                entity.setRoute(rs.getString("route"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Place> getEntitys() {
        List<Place> reData = new ArrayList<Place>();
        String sql = "select id,cityId,regionId,name,address,`desc`,lng,lat,route,status,addTime from SG_Place where status > ? order by id desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Place entity = new Place();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("cityId").toString()));
                entity.setRegionId(Integer.parseInt(list.get(i).get("regionId").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setAddress(list.get(i).get("address").toString());
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setLng(Float.parseFloat(list.get(i).get("lng").toString()));
                entity.setLat(Float.parseFloat(list.get(i).get("lat").toString()));
                if (list.get(i).get("route") == null){
                    entity.setRoute("");
                }else {
                    entity.setRoute(list.get(i).get("route").toString());
                }
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Place entity) {
        String sql = "insert into SG_Place(cityId,regionId,name, address, `desc`, lng, lat, route, status,addTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?,NOW()) ";
        Object [] params = new Object[]{entity.getCityId(), entity.getRegionId(), entity.getName(), entity.getAddress(), entity.getDesc(), entity.getLng(), entity.getLat(), entity.getRoute(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int insertKey(Place entity) {
        final Place place = entity;
        final String sql = "insert into SG_Place (cityId, regionId, name, address, `desc`, lng, lat, route,  status) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = jdbcTemplate.update( new PreparedStatementCreator(){
            public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException{

                int i = 0;
                java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(++i, place.getCityId());
                ps.setInt(++i, place.getRegionId());
                ps.setString(++i, place.getName());
                ps.setString(++i, place.getAddress());
                ps.setString(++i, place.getDesc());
                ps.setDouble(++i, place.getLng());
                ps.setDouble(++i, place.getLat());
                ps.setString(++i, place.getRoute());
                ps.setInt(++i, Quantity.STATUS_ZERO);
                return ps;
            }
        },keyHolder);
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;

    }

    @Override
    public int update(Place entity) {
        String sql = "update SG_Place set cityId = ?, regionId = ?, name = ? , address = ?, `desc` = ?, lng = ?, lat = ?, route = ? where id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getRegionId(), entity.getName(), entity.getAddress(), entity.getDesc(), entity.getLng(), entity.getLat(), entity.getRoute(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Place set status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Place formEntity(HttpServletRequest request, int id) {
        Place entity = new Place();
        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        if (null == request.getParameter("regionId") || request.getParameter("regionId").equals("")){
            if (null != request.getParameter("quid") && !request.getParameter("quid").equals("")){
                entity.setRegionId(Integer.parseInt(request.getParameter("quid")));
            }
        }else {
            entity.setRegionId(Integer.parseInt(request.getParameter("regionId")));
        }
        entity.setName(request.getParameter("name"));
        entity.setAddress(request.getParameter("address"));
        entity.setDesc(request.getParameter("desc"));
        String lngLat = request.getParameter("lng_lat");
        if (null != lngLat && !lngLat.equals("") && lngLat.indexOf(",") > 0){
            String [] lng_lat = request.getParameter("lng_lat").split(",");
            entity.setLng(Double.parseDouble(lng_lat[0]));
            entity.setLat(Double.parseDouble(lng_lat[1]));
        }else{
            entity.setLng(Double.parseDouble("0"));
            entity.setLat(Double.parseDouble("0"));
        }

        entity.setRoute(request.getParameter("route"));
        return entity;
    }

    /**
     * 获取城市，地区，街道的json
     * @return
     */
    @Override
    public String getDataJsonStr(int placeid){
        List<City> ls = new ArrayList<City>();
        if (placeid == 0) {
            List<City> city_ls = cityService.getEntitys();
            if (city_ls.size() > 0) {
                for (int i = 0; i < city_ls.size(); i++) {
                    City city = city_ls.get(i);
                    List<Region> reg_ls = regionService.getEntitysBycpId(city.getId(), 0);
                    if (reg_ls.size() > 0) {
                        for (int j = 0; j < reg_ls.size(); j++) {
                            Region region = reg_ls.get(j);
                            List<Region> region_ls = regionService.getEntitysByparentId(region.getId());
                            region.setRegchild(region_ls);
                        }
                        city.setRegions(reg_ls);
                    }
                }
            }
            ls.addAll(city_ls);
        }else{
            Place place = this.get(placeid);
            int cityid = place.getCityId();
            int regid = place.getRegionId();
            List<City> city_ls = cityService.getEntitys();
            if (city_ls.size() > 0) {
                for (int i = 0; i < city_ls.size(); i++) {
                    City city = city_ls.get(i);
                    if (city.getId() == cityid){
                        city.setSelected(1);
                    }
                    List<Region> reg_ls = regionService.getEntitysBycpId(city.getId(), 0);
                    if (reg_ls.size() > 0) {
                        for (int j = 0; j < reg_ls.size(); j++) {
                            Region region = reg_ls.get(j);
                            int parentid = region.getId();
                            Region rsssss = regionService.get(regid);
                            if (region.getCityId()==cityid && region.getId() == rsssss.getParentId()){
                                region.setSelected(1);
                            }
                            List<Region> region_ls = regionService.getEntitysByparentId(region.getId());
                            for (int k = 0; k < region_ls.size(); k++) {
                                if (region_ls.get(k).getCityId()==cityid && region_ls.get(k).getId() == regid && region_ls.get(k).getParentId() == parentid){
                                    region_ls.get(k).setSelected(1);
                                }
                            }
                            region.setRegchild(region_ls);
                        }
                        city.setRegions(reg_ls);
                    }
                }
            }
            ls.addAll(city_ls);
        }
        String reData = JSON.toJSONString(ls);
        return reData;
    }
}
