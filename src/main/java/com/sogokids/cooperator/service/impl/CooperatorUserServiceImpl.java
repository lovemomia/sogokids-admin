package com.sogokids.cooperator.service.impl;

import com.sogokids.cooperator.model.CooperatorUser;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.cooperator.service.CooperatorUserService;
import com.sogokids.utils.encrypt.Base64;
import com.sogokids.utils.util.Quantity;
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
 * Created by hoze on 16/3/31.
 */
@Service
public class CooperatorUserServiceImpl implements CooperatorUserService {

    @Autowired
    private CooperatorService cooperatorService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CooperatorUser get(int id) {
        String sql = "select Id, CooperatorId, Name, Password, Status, AddTime from SG_CooperatorUser where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CooperatorUser entity = new CooperatorUser();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                int cooperatorId = rs.getInt("CooperatorId");
                entity.setCooperatorId(cooperatorId);
                entity.setName(rs.getString("Name"));
                entity.setPassword(new String(new Base64().decode(rs.getString("Password"))));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
                entity.setCooperator(cooperatorService.get(cooperatorId));
            }
        });

        return entity;
    }

    @Override
    public List<CooperatorUser> getEntitys() {
        List<CooperatorUser> reData = new ArrayList<CooperatorUser>();
        String sql = "select Id, CooperatorId, Name, Password, Status, AddTime from SG_CooperatorUser where Status > ? ";
        Object[] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0) {
            try {
                for (int i = 0; i < list.size(); i++) {
                    CooperatorUser entity = new CooperatorUser();

                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    int cooperatorId = Integer.parseInt(list.get(i).get("CooperatorId").toString());
                    entity.setCooperatorId(cooperatorId);
                    entity.setName(list.get(i).get("Name").toString());
                    entity.setPassword(new String(new Base64().decode(list.get(i).get("Password").toString())));
                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime") == null ? "" : list.get(i).get("AddTime").toString());
                    entity.setCooperator(cooperatorService.get(cooperatorId));

                    reData.add(entity);
                }
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }

        return reData;
    }

    @Override
    public int insert(CooperatorUser entity) {
        String sql = "insert into SG_CooperatorUser(CooperatorId, Name, Password, Status, AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCooperatorId(), entity.getName(), entity.getPassword(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }

        return reData;
    }

    @Override
    public int update(CooperatorUser entity) {
        String sql = "update SG_CooperatorUser set CooperatorId = ?, Name = ?, Password = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCooperatorId(), entity.getName(), entity.getPassword(), entity.getId()};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CooperatorUser set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public CooperatorUser formEntity(HttpServletRequest request, int id) {
        CooperatorUser entity = new CooperatorUser();

        entity.setId(id);
        entity.setCooperatorId(Integer.parseInt(request.getParameter("cooperatorId")));
        entity.setName(StringUtils.isEmpty(request.getParameter("name"))?"":request.getParameter("name"));
        String password = request.getParameter("password");
        String encodeStr = new Base64().encode(password.getBytes());
        entity.setPassword(encodeStr);

        return entity;
    }

    @Override
    public CooperatorUser login(String name, String password) {
        String decodeStr = new Base64().encode(password.getBytes());
        String sql = "select Id, CooperatorId, Name, Password, Status, AddTime from SG_CooperatorUser where Name = ? and Password = ? and Status > ? ";
        final Object [] params = new Object[]{name,decodeStr,Quantity.STATUS_ZERO};
        final CooperatorUser entity = new CooperatorUser();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setCooperatorId(rs.getInt("CooperatorId"));
                entity.setName(rs.getString("Name"));
//                entity.setPassword(new String(new Base64().decode(rs.getString("Password"))));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
                entity.setCooperator(cooperatorService.get(rs.getInt("CooperatorId")));
            }
        });

        return entity;
    }

    @Override
    public int verifyName(String name) {
        int reData = 1;
        String sql = "select Id, CooperatorId, Name, Status, AddTime from SG_CooperatorUser where Status > ? and Name = ? ";
        Object[] params = new Object[]{Quantity.STATUS_ZERO,name};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            reData = 0;
        }

        return reData;
    }
}
