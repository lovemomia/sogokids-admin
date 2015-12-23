package com.sogokids.user.service.impl;

import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.RoleFunc;
import com.sogokids.user.service.RoleFuncService;
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
public class RoleFuncServiceImpl implements RoleFuncService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RoleFunc get(int id) {
        String sql = "select Id,RoleId,FuncId from SG_RoleFunc where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final RoleFunc entity = new RoleFunc();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setFuncId(rs.getInt("FuncId"));
                entity.setRoleId(rs.getInt("RoleId"));
            }
        });

        return entity;
    }

    @Override
    public List<RoleFunc> getEntityList() {
        return null;
    }

    @Override
    public List<RoleFunc> getRoleFunc(int roleId) {
        List<RoleFunc> reData = new ArrayList<RoleFunc>();
        String sql = "select Id,FuncId,RoleId from SG_RoleFunc where RoleId = ?  ";
        Object [] params = new Object[]{roleId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                RoleFunc entity = new RoleFunc();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setFuncId(Integer.parseInt(list.get(i).get("FuncId").toString()));
                entity.setRoleId(Integer.parseInt(list.get(i).get("RoleId").toString()));
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(RoleFunc entity) {
        String sql = "insert into SG_RoleFunc(RoleId,FuncId) value(?, ?) ";
        Object [] params = new Object[]{entity.getRoleId(), entity.getFuncId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(RoleFunc entity) {
        return 0;
    }

    @Override
    public int delete(RoleFunc entity) {
        String sql = "delete from SG_RoleFunc where RoleId = ? and FuncId = ? ";
        Object [] params = new Object[]{entity.getRoleId(), entity.getFuncId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public RoleFunc formEntity(int funcId, int roleId) {

        RoleFunc entity = new RoleFunc();
        entity.setFuncId(funcId);
        entity.setRoleId(roleId);

        return entity;
    }
}
