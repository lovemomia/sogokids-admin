package com.sogokids.query.service.impl;

import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.utils.util.Quantity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "select Id,Mobile,NickName from SG_User where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Customer entity = new Customer();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setMobile(rs.getString("Mobile"));
                entity.setNickName(rs.getString("NickName"));
            }
        });

        return entity;
    }

    @Override
    public Customer getCustomerByMobile(String mobile){
        String sql = "select Id,Mobile,NickName from SG_User where Mobile = ? and Status > ? ";
        final Object [] params = new Object[]{mobile, Quantity.STATUS_ZERO};
        final Customer entity = new Customer();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setMobile(rs.getString("Mobile"));
                entity.setNickName(rs.getString("NickName"));
            }
        });

        return entity;
    }

    @Override
    public int updateCustomer(int id) {
        String sql = "update SG_User set Role = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_TWO, id};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public String getUserInfo(List<Long> uids){
        StringBuffer sb = new StringBuffer();

        for (Long uid : uids){
            Customer customer = this.getCustomer(Integer.parseInt(uid.toString()));
            if (customer.getId() > 0) {
                sb.append(customer.getNickName()).append(",");
            }else{
                sb.append(uid+"无用户,");
            }
        }

        String content = sb.toString();
        content = content.substring(0,content.length()-1);
        content =  "[" + content + "]";
        return content;

    }
}
