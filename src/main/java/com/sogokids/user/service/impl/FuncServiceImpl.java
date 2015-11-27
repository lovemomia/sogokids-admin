package com.sogokids.user.service.impl;

import com.sogokids.user.model.Func;
import com.sogokids.user.model.Role;
import com.sogokids.user.model.User;
import com.sogokids.user.service.FuncService;
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
public class FuncServiceImpl implements FuncService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Func get(int id) {
        String sql = "select id,Name,`Desc`,Status,AddTime,Code from SG_Func where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Func entity = new Func();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setCode(rs.getString("Code"));
                entity.setName(rs.getString("Name"));
                entity.setDesc(rs.getString("Desc"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Func> getEntityList() {
        List<Func> reData = new ArrayList<Func>();
        String sql = "select id,Name,`Desc`,Status,AddTime,Code from SG_Func where Status > ?  ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Func entity = new Func();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCode(list.get(i).get("Code").toString());
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
    public int insert(Func entity) {
        String sql = "insert into SG_Func(Code,Name,`Desc`,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCode(), entity.getName(), entity.getDesc(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Func entity) {
        String sql = "update SG_Func set Code = ?, Name = ? , `Desc` = ? where id = ? ";
        Object [] params = new Object[]{entity.getCode(), entity.getName(), entity.getDesc(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Func set Status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Func formEntity(HttpServletRequest req, int id) {
        Func entity = new Func();
        entity.setId(id);
        entity.setCode(req.getParameter("code"));
        entity.setName(req.getParameter("name"));
        entity.setDesc(req.getParameter("desc"));

        return entity;
    }
}
