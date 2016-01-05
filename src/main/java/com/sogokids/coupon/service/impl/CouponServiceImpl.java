package com.sogokids.coupon.service.impl;

import com.sogokids.coupon.model.Coupon;
import com.sogokids.coupon.service.CouponService;
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
 * Created by hoze on 15/11/15.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Coupon get(int id) {
        String sql = "select Id, Type, Src, Count, Title, `Desc`, Discount, Consumption, Accumulation, TimeType, Time, TimeUnit, StartTime, EndTime, OnlineTime, OfflineTime, Status, AddTime from SG_Coupon where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Coupon entity = new Coupon();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setType(rs.getInt("Type"));
                entity.setSrc(rs.getInt("Src"));
                entity.setCount(rs.getInt("Count"));
                entity.setTitle(rs.getString("Title"));
                entity.setDesc(rs.getString("Desc"));
                entity.setDiscount(rs.getFloat("Discount"));
                entity.setConsumption(rs.getFloat("Consumption"));
                entity.setAccumulation(rs.getInt("Accumulation"));
                entity.setTimeType(rs.getInt("TimeType"));
                entity.setTime(rs.getInt("Time"));
                entity.setTimeUnit(rs.getInt("TimeUnit"));
                entity.setStartTime(rs.getString("StartTime"));
                entity.setEndTime(rs.getString("EndTime"));
                entity.setOnlineTime(rs.getString("OnlineTime"));
                entity.setOfflineTime(rs.getString("OfflineTime"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Coupon> getEntitys() {
        List<Coupon> reData = new ArrayList<Coupon>();
        String sql = "select Id, Type, Src, Count, Title, `Desc`, Discount, Consumption, Accumulation, TimeType, Time, TimeUnit, StartTime, EndTime, OnlineTime, OfflineTime, Status, AddTime from SG_Coupon where Status > ? order by Id desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            try {
                for (int i = 0; i < list.size(); i++) {
                    Coupon entity = new Coupon();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setType(Integer.parseInt(list.get(i).get("Type").toString()));
                    entity.setSrc(Integer.parseInt(list.get(i).get("Src").toString()));
                    entity.setCount(Integer.parseInt(list.get(i).get("Count").toString()));
                    entity.setTitle(list.get(i).get("Title").toString());
                    entity.setDesc(list.get(i).get("Desc").toString());
                    entity.setDiscount(Float.parseFloat(list.get(i).get("Discount").toString()));
                    entity.setConsumption(Float.parseFloat(list.get(i).get("Consumption").toString()));
                    entity.setAccumulation(Integer.parseInt(list.get(i).get("Accumulation").toString()));
                    entity.setTimeType(Integer.parseInt(list.get(i).get("TimeType").toString()));
                    entity.setTime(Integer.parseInt(list.get(i).get("Time").toString()));
                    entity.setTimeUnit(Integer.parseInt(list.get(i).get("TimeUnit").toString()));
                    entity.setStartTime(list.get(i).get("StartTime").toString());
                    entity.setEndTime(list.get(i).get("EndTime").toString());
                    entity.setOnlineTime(list.get(i).get("OnlineTime").toString());
                    entity.setOfflineTime(list.get(i).get("OfflineTime").toString());
                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime").toString());
                    reData.add(entity);
                }
            }catch (Exception _ex){
                _ex.printStackTrace();
            }
        }

        return reData;
    }

    @Override
    public int insert(Coupon entity) {
        String sql = "insert into SG_Coupon(Type, Src, Count, Title, `Desc`, Discount, Consumption, TimeType, Time, TimeUnit, StartTime, EndTime, OnlineTime, OfflineTime, Status, AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getType(), entity.getSrc(), entity.getCount(), entity.getTitle(), entity.getDesc(), entity.getDiscount(), entity.getConsumption(), entity.getTimeType(), entity.getTime(),entity.getTimeUnit(), entity.getStartTime(), entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Coupon entity) {
        String sql = "update SG_Coupon set Type = ? , Src = ?, Count = ?, Title = ?, `Desc` = ?, Discount = ?, Consumption = ?, TimeType = ?, Time = ?, TimeUnit = ?, StartTime = ?, EndTime = ?, OnlineTime = ?, OfflineTime = ? where Id = ? ";
        Object [] params = new Object[]{entity.getType(), entity.getSrc(), entity.getCount(), entity.getTitle(), entity.getDesc(), entity.getDiscount(), entity.getConsumption(), entity.getTimeType(), entity.getTime(),entity.getTimeUnit(), entity.getStartTime(), entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Coupon set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Coupon formEntity(HttpServletRequest request, int id) {
        Coupon entity = new Coupon();
        entity.setId(id);
        entity.setType(Integer.parseInt(request.getParameter("type")));
        entity.setSrc(Integer.parseInt(request.getParameter("src")));
        entity.setCount(Integer.parseInt(request.getParameter("count")));
        entity.setTitle(request.getParameter("title"));
        entity.setDesc(request.getParameter("desc"));
        entity.setDiscount(Float.parseFloat(request.getParameter("discount")));
        entity.setConsumption(Float.parseFloat(request.getParameter("consumption")));

        entity.setTimeType(Integer.parseInt(request.getParameter("timeType")));
        entity.setTime(Integer.parseInt(request.getParameter("time")));
        entity.setTimeUnit(Integer.parseInt(request.getParameter("timeUnit")));

        entity.setStartTime(request.getParameter("startTime"));
        entity.setEndTime(request.getParameter("endTime"));
        entity.setOnlineTime(request.getParameter("onlineTime"));
        entity.setOfflineTime(request.getParameter("offlineTime"));
        return entity;
    }
}
