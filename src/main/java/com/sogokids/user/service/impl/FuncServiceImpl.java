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
        String sql = "select id,Name,`Desc`,Status,AddTime,Link from SG_Func where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Func entity = new Func();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setLink(rs.getString("Link"));
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
        String sql = "select id,Name,`Desc`,Status,AddTime,Link from SG_Func where Status > ?  ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Func entity = new Func();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setLink(list.get(i).get("Link").toString());
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
        String sql = "insert into SG_Func(Link,Name,`Desc`,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getLink(), entity.getName(), entity.getDesc(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Func entity) {
        String sql = "update SG_Func set Link = ?, Name = ? , `Desc` = ? where id = ? ";
        Object [] params = new Object[]{entity.getLink(), entity.getName(), entity.getDesc(), entity.getId()};
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
        entity.setLink(req.getParameter("link"));
        entity.setName(req.getParameter("name"));
        entity.setDesc(req.getParameter("desc"));

        return entity;
    }

    @Override
    public boolean isFunc(int uid,String link){
        boolean reData = false;
        String sql = "select a.Id,a.Username,c.Id as RId,c.Name,e.Id as FId,e.Name as funcName,e.Link from sogokids.SG_Admin a,sogokids.SG_AdminRole b,sogokids.SG_Role c,sogokids.SG_RoleFunc d,sogokids.SG_Func e where a.Status > 0 and c.Status > 0 and e.Status > 0 and a.Id = b.AdminId and c.Id = b.RoleId and c.Id = d.RoleId and e.Id = d.FuncId and a.Id = ? and link = ? ";
        Object [] params = new Object[]{uid,link};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if (list.size() > 0){
            reData = true;
        }

        return reData;
    }

    @Override
    public List<Func> getYList(int r_id) {
        List<Func> reData = new ArrayList<Func>();
        String sql = "select * from sogokids.SG_Func where status > 0 and id in (select b.FuncId from sogokids.SG_Role a,sogokids.SG_RoleFunc b where a.Id = b.RoleId and a.id = ? )";
        Object [] params = new Object[]{r_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Func entity = new Func();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setLink(list.get(i).get("Link").toString());
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
    public List<Func> getWList(int r_id) {
        List<Func> reData = new ArrayList<Func>();
        String sql = "select * from sogokids.SG_Func where status > 0 and id not in (select b.FuncId from sogokids.SG_Role a,sogokids.SG_RoleFunc b where a.Id = b.RoleId and a.id = ? )";
        Object [] params = new Object[]{r_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Func entity = new Func();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setLink(list.get(i).get("Link").toString());
                entity.setName(list.get(i).get("Name").toString());
                entity.setDesc(list.get(i).get("Desc").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
