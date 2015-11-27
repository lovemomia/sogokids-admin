package com.sogokids.order.service.impl;

import com.sogokids.order.model.Payment;
import com.sogokids.order.service.PaymentService;
import com.sogokids.utils.util.Quantity;
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
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Payment getPayment(int order_id) {
        String sql = "select Id,OrderId,Payer,FinishTime,PayType,TradeNo,Fee,Status,AddTime from SG_SubjectPayment where Status > ? and OrderId = ? ";
        final Object [] params = new Object[]{Quantity.STATUS_ZERO, order_id};
        final Payment entity = new Payment();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setOrderId(rs.getInt("OrderId"));
                entity.setPayer(rs.getString("Payer"));
                entity.setFinishTime(rs.getString("FinishTime"));
                entity.setPayType(rs.getInt("PayType"));
                entity.setTradeNo(rs.getString("TradeNo"));
                entity.setFee(rs.getBigDecimal("Fee"));
            }
        });

        return entity;
    }
}
