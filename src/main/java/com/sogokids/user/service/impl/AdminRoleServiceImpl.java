package com.sogokids.user.service.impl;

import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.Role;
import com.sogokids.user.service.AdminRoleService;
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
 * Created by hoze on 15/10/23.
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AdminRole get(int id) {
        String sql = "select Id,AdminId,RoleId from SG_AdminRole where Id = ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final AdminRole entity = new AdminRole();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setAdminId(rs.getInt("AdminId"));
                entity.setRoleId(rs.getInt("RoleId"));
            }
        });

        return entity;
    }

    @Override
    public List<AdminRole> getEntityList() {
        return null;
    }

    @Override
    public List<AdminRole> getAdminRole(int adminId) {
        List<AdminRole> reData = new ArrayList<AdminRole>();
        String sql = "select Id,AdminId,RoleId from SG_AdminRole where adminId = ?  ";
        Object [] params = new Object[]{adminId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                AdminRole entity = new AdminRole();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setAdminId(Integer.parseInt(list.get(i).get("AdminId").toString()));
                entity.setRoleId(Integer.parseInt(list.get(i).get("RoleId").toString()));
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(AdminRole entity) {
        String sql = "insert into SG_AdminRole(AdminId,RoleId) value(?, ?) ";
        Object [] params = new Object[]{entity.getAdminId(), entity.getRoleId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(AdminRole entity) {
        return 0;
    }

    @Override
    public int delete(AdminRole entity) {
        String sql = "delete from SG_AdminRole where AdminId = ? and RoleId = ? ";
        Object [] params = new Object[]{entity.getAdminId(), entity.getRoleId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public AdminRole formEntity(int adminId, int roleId) {
        AdminRole entity = new AdminRole();
        entity.setAdminId(adminId);
        entity.setRoleId(roleId);

        return entity;
    }
}
