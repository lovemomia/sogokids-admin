package com.sogokids.system.service.impl;

import com.sogokids.system.model.AppVersion;
import com.sogokids.system.service.AppVersionService;
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
 * Created by hoze on 15/12/21.
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<AppVersion> getEntitys() {
        List<AppVersion> reData = new ArrayList<AppVersion>();
        String sql = "select Id,VersionCode,VersionDesc,Status,AddTime from SG_AppVersion where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                AppVersion entity = new AppVersion();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setVersionCode(list.get(i).get("VersionCode").toString());
                entity.setVersionDesc(list.get(i).get("VersionDesc").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public AppVersion get(int id) {
        String sql = "select Id,VersionCode,VersionDesc,Status,AddTime from SG_AppVersion where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final AppVersion entity = new AppVersion();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setVersionCode(rs.getString("VersionCode"));
                entity.setVersionDesc(rs.getString("VersionDesc"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(AppVersion entity) {
        String sql = "insert into SG_AppVersion(VersionCode,VersionDesc,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getVersionCode(), entity.getVersionDesc(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(AppVersion entity) {
        String sql = "update SG_AppVersion set VersionCode = ?, VersionDesc = ?  where Id = ? ";
        Object [] params = new Object[]{entity.getVersionCode(), entity.getVersionDesc(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_AppVersion set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public AppVersion formEntity(HttpServletRequest request, int id) {
        AppVersion entity = new AppVersion();
        entity.setId(id);
        entity.setVersionCode(request.getParameter("code"));
        entity.setVersionDesc(request.getParameter("desc"));

        return entity;
    }
}
