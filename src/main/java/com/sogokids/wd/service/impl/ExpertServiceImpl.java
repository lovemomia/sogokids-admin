package com.sogokids.wd.service.impl;

import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.utils.util.Quantity;
import com.sogokids.wd.model.Expert;
import com.sogokids.wd.model.ExpertBanner;
import com.sogokids.wd.service.ExpertService;
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
public class ExpertServiceImpl implements ExpertService {

    @Autowired
    private CustomerService customerService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Expert> getEntitys() {
        List<Expert> reData = new ArrayList<Expert>();
        String sql = "select Id,UserId,Name,Cover,Intro,Gender,Address,Mobile,Status,AddTime from SG_Expert where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Expert entity = new Expert();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setName(list.get(i).get("Name").toString());
                entity.setCover(list.get(i).get("Cover").toString());
                entity.setIntro(list.get(i).get("Intro").toString());
                entity.setGender(list.get(i).get("Gender").toString());
                entity.setAddress(list.get(i).get("Address").toString());
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setUserName(customerService.getCustomer(Integer.parseInt(list.get(i).get("UserId").toString())).getNickName());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Expert get(int id) {
        String sql = "select Id,UserId,Name,Cover,Intro,Gender,Address,Mobile,Status,AddTime from SG_Expert where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Expert entity = new Expert();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setCover(rs.getString("Cover"));
                entity.setName(rs.getString("Name"));
                entity.setIntro(rs.getString("Intro"));
                entity.setGender(rs.getString("Gender"));
                entity.setAddress(rs.getString("Address"));
                entity.setMobile(rs.getString("Mobile"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(Expert entity) {
        String sql = "insert into SG_Expert(UserId,Name,Cover,Intro,Gender,Address,Mobile,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getCover(), entity.getIntro(), entity.getGender(), entity.getAddress(), entity.getMobile(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Expert entity) {
        String sql = "update SG_Expert set UserId = ?, Name = ?, Cover = ?, Intro = ?, Gender = ?, Address = ?, Mobile = ? where Id = ? ";
        Object [] params = new Object[]{entity.getUserId(), entity.getName(), entity.getCover(), entity.getIntro(), entity.getGender(), entity.getAddress(), entity.getMobile(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Expert set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Expert formEntity(HttpServletRequest request, int id) {
        Expert entity = new Expert();

        entity.setId(id);
        entity.setUserId(Integer.parseInt(request.getParameter("userId")));
        entity.setName(request.getParameter("name"));
        entity.setCover(request.getParameter("cover"));
        entity.setIntro(request.getParameter("intro"));
        int gender_int = Integer.parseInt(request.getParameter("gender"));
        if (gender_int == 2){
            entity.setGender("男");
        }else{
            entity.setGender("女");
        }

        entity.setAddress(request.getParameter("address"));
        entity.setMobile(request.getParameter("mobile"));

        return entity;
    }
}
