package com.sogokids.user.service.impl;

import com.sogokids.user.model.Role;
import com.sogokids.user.service.RoleService;
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
 * Created by hoze on 15/10/21.
 */
@Service
public class RoleServiceImpl implements RoleService{

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Role get(int id) {
        String sql = "select id,`Name`,`Desc`,Status,AddTime from SG_Role where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Role entity = new Role();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setName(rs.getString("Name"));
                entity.setDesc(rs.getString("Desc"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Role> getEntityList() {
        List<Role> reData = new ArrayList<Role>();
        String sql = "select Id,Name,`Desc`,Status,AddTime from SG_Role where Status > ?  ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Role entity = new Role();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setDesc(list.get(i).get("Desc").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(Role entity) {
        String sql = "insert into SG_Role(`Name`,`Desc`,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getName(), entity.getDesc(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Role entity) {
        String sql = "update SG_Role set Name = ? , `Desc` = ? where id = ? ";
        Object [] params = new Object[]{entity.getName(), entity.getDesc(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Role set Status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Role formEntity(HttpServletRequest req, int id) {

        Role entity = new Role();
        entity.setId(id);
        entity.setName(req.getParameter("name"));
        entity.setDesc(req.getParameter("desc"));

        return entity;
    }

    @Override
    public List<Role> getYList(int uid) {
        List<Role> reData = new ArrayList<Role>();
        String sql = "select * from sogokids.SG_Role where status > 0 and Id in (select b.RoleId from sogokids.SG_Admin a,sogokids.SG_AdminRole b where a.status > 0 and a.Id = b.AdminId and a.Id = ? )  ";
        Object [] params = new Object[]{uid};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Role entity = new Role();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setDesc(list.get(i).get("Desc").toString());
                reData.add(entity);
            }
        }

        return reData;
    }


    @Override
    public List<Role> getWList(int uid) {
        List<Role> reData = new ArrayList<Role>();
        String sql = "select * from sogokids.SG_Role where status > 0 and Id not in (select b.RoleId from sogokids.SG_Admin a,sogokids.SG_AdminRole b where a.status > 0 and a.Id = b.AdminId and a.Id = ? ) ";
        Object [] params = new Object[]{uid};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Role entity = new Role();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setDesc(list.get(i).get("Desc").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
