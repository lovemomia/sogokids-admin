package com.sogokids.wd.service.impl;

import com.sogokids.system.service.CityService;
import com.sogokids.utils.util.Quantity;
import com.sogokids.wd.model.ExpertBanner;
import com.sogokids.wd.service.ExpertBannerService;
import org.apache.commons.lang3.StringUtils;
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
 * Created by hoze on 16/6/27.
 */
@Service
public class ExpertBannerServiceImpl implements ExpertBannerService {

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
    public List<ExpertBanner> getEntitys() {
        List<ExpertBanner> reData = new ArrayList<ExpertBanner>();
        String sql = "select Id,CityId,Cover,Action,Weight,Platform,Version,Status,AddTime from SG_ExpertBanner where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ExpertBanner entity = new ExpertBanner();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCityId(Integer.parseInt(list.get(i).get("CityId").toString()));
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setAction(list.get(i).get("Action").toString());
                entity.setWeight(Integer.parseInt(list.get(i).get("Weight").toString()));
                entity.setPlatform(Integer.parseInt(list.get(i).get("Platform").toString()));
                entity.setVersion(list.get(i).get("Version").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setCityName(cityService.get(Integer.parseInt(list.get(i).get("CityId").toString())).getName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public ExpertBanner get(int id) {
        String sql = "select Id,CityId,Cover,Action,Weight,Platform,Version,Status,AddTime from SG_ExpertBanner where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final ExpertBanner entity = new ExpertBanner();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCityId(rs.getInt("CityId"));
                entity.setCover(rs.getString("Cover"));
                entity.setAction(rs.getString("Action"));
                entity.setWeight(rs.getInt("Weight"));
                entity.setPlatform(rs.getInt("Platform"));
                entity.setVersion(rs.getString("Version"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(ExpertBanner entity) {
        String sql = "insert into SG_ExpertBanner(CityId,Cover,Action,Platform,Version,Weight,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCityId(), entity.getCover(), entity.getAction(), entity.getPlatform(), entity.getVersion(), entity.getWeight(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(ExpertBanner entity) {
        String sql = "update SG_ExpertBanner set CityId = ?, Cover = ?, Action = ?, Platform = ?, Version = ?, Weight = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCityId(), entity.getCover(), entity.getAction(), entity.getPlatform(), entity.getVersion(), entity.getWeight(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_ExpertBanner set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public ExpertBanner formEntity(HttpServletRequest request, int id) {
        ExpertBanner entity = new ExpertBanner();

        entity.setId(id);
        entity.setCityId(Integer.parseInt(request.getParameter("cityId")));
        entity.setCover(request.getParameter("cover"));
        entity.setAction(request.getParameter("action"));
        int platform = Integer.parseInt(request.getParameter("platform"));
        entity.setPlatform(platform);
        if (platform == 1){
            entity.setVersion(request.getParameter("version"));
        }else{
            entity.setVersion("");
        }

        entity.setWeight(Integer.parseInt(StringUtils.isEmpty(request.getParameter("weight"))?"0":request.getParameter("weight")));

        return entity;
    }
}
