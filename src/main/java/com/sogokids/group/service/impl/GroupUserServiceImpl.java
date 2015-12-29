package com.sogokids.group.service.impl;

import com.sogokids.group.model.GroupUser;
import com.sogokids.group.service.GroupUserService;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Created by hoze on 15/12/25.
 */
@Service
public class GroupUserServiceImpl implements GroupUserService {

    private final Logger log = LoggerFactory.getLogger(GroupUserServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GroupUser> getEntitys() {
        List<GroupUser> reData = new ArrayList<GroupUser>();
        String sql = "select Id,GroupId,UserId,Status,AddTime from SG_GroupUser where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                GroupUser entity = new GroupUser();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setGroupId(Integer.parseInt(list.get(i).get("GroupId").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public GroupUser get(int id) {
        String sql = "select Id,GroupId,UserId,Status,AddTime from SG_GroupUser where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final GroupUser entity = new GroupUser();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setGroupId(rs.getInt("GroupId"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public GroupUser isget(int gid, int uid) {
        String sql = "select Id,GroupId,UserId,Status,AddTime from SG_GroupUser where GroupId = ? and UserId = ? and Status > ? ";
        final Object [] params = new Object[]{gid, uid, Quantity.STATUS_ZERO};
        final GroupUser entity = new GroupUser();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setGroupId(rs.getInt("GroupId"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(GroupUser entity) {
        String sql = "insert into SG_GroupUser(GroupId,UserId,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getGroupId(), entity.getUserId(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            log.info("开始添加分组用户信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束添加分组用户信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(GroupUser entity) {
        String sql = "update SG_GroupUser set GroupId = ?, UserId = ? where Id = ? ";
        Object [] params = new Object[]{entity.getGroupId(), entity.getUserId(), entity.getId()};
        int reData = 0;
        try{
            log.info("开始修改分组用户信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束修改分组用户信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_GroupUser set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        log.info("开始删除分组用户信息!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束删除分组用户信息!");
        return reData;
    }

    @Override
    public GroupUser formEntity(HttpServletRequest request, int id) {
        GroupUser entity = new GroupUser();

        entity.setId(id);
        entity.setGroupId(Integer.parseInt(request.getParameter("groupId")));
        entity.setUserId(Integer.parseInt(request.getParameter("userId")));

        return entity;
    }
}
