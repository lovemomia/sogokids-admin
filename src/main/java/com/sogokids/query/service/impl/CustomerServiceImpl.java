package com.sogokids.query.service.impl;

import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String sql = "select Id,Mobile,NickName from SG_User where Id = ? ";
        final Object [] params = new Object[]{id};
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
}
