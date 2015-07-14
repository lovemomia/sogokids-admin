package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.entity.Coupon;
import cn.momia.admin.web.service.CouponService;
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
 * Created by hoze on 15/7/14.
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
        String sql = "select id, type, `usage`, title, `desc`, discount, consumption, accumulation, startTime, endTime, status, addTime from t_coupon where id = ? and status > ? ";
        final Object [] params = new Object[]{id, FinalUtil.DEL_STATUS};
        final Coupon entity = new Coupon();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setType(rs.getInt("type"));
                entity.setUsage(rs.getInt("usage"));
                entity.setTitle(rs.getString("title"));
                entity.setDesc(rs.getString("desc"));
                entity.setDiscount(rs.getFloat("discount"));
                entity.setConsumption(rs.getFloat("consumption"));
                entity.setAccumulation(rs.getInt("accumulation"));
                entity.setStartTime(rs.getString("startTime"));
                entity.setEndTime(rs.getString("endTime"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<Coupon> getEntitys() {
        List<Coupon> reData = new ArrayList<Coupon>();
        String sql = "select id, type, `usage`, title, `desc`, discount, consumption, accumulation, startTime, endTime, status, addTime from t_coupon where status > ? order by id desc";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Coupon entity = new Coupon();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setUsage(Integer.parseInt(list.get(i).get("usage").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setDiscount(Float.parseFloat(list.get(i).get("discount").toString()));
                entity.setConsumption(Float.parseFloat(list.get(i).get("consumption").toString()));
                entity.setAccumulation(Integer.parseInt(list.get(i).get("accumulation").toString()));
                entity.setStartTime(list.get(i).get("startTime").toString());
                entity.setEndTime(list.get(i).get("endTime").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;

    }

    @Override
    public int insert(Coupon entity) {

        String sql = "insert into t_coupon(type, `usage`, title, `desc`, discount, consumption,startTime,endTime,status,addTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getType(), entity.getUsage(), entity.getTitle(), entity.getDesc(), entity.getDiscount(), entity.getConsumption(), entity.getStartTime(), entity.getEndTime(), FinalUtil.ADD_STATUS};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(Coupon entity) {
        String sql = "update t_coupon set type = ? , `usage` = ?, title = ?, `desc` = ?, discount = ?, consumption = ?, startTime = ?, endTime = ? where id = ? ";
        Object [] params = new Object[]{entity.getType(), entity.getUsage(), entity.getTitle(), entity.getDesc(), entity.getDiscount(), entity.getConsumption(), entity.getStartTime(), entity.getEndTime(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update t_coupon set status = ? where id = ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Coupon formEntity(HttpServletRequest request, int id) {
        Coupon entity = new Coupon();
        entity.setId(id);
        entity.setType(Integer.parseInt(request.getParameter("type")));
        entity.setUsage(Integer.parseInt(request.getParameter("usage")));
        entity.setTitle(request.getParameter("title"));
        entity.setDesc(request.getParameter("desc"));
        entity.setDiscount(Float.parseFloat(request.getParameter("discount")));
        entity.setConsumption(Float.parseFloat(request.getParameter("consumption")));
        entity.setStartTime(request.getParameter("startTime"));
        entity.setEndTime(request.getParameter("endTime"));
        return entity;
    }

    @Override
    public List<Coupon> getQueryPages(int start_row, int end_row) {
        List<Coupon> reData = new ArrayList<Coupon>();
        String sql = "select id, type, `usage`, title, `desc`, discount, consumption, accumulation, startTime, endTime, status, addTime from t_coupon where status > ? order by id desc limit "+start_row+","+end_row;
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Coupon entity = new Coupon();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setType(Integer.parseInt(list.get(i).get("type").toString()));
                entity.setUsage(Integer.parseInt(list.get(i).get("usage").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setDesc(list.get(i).get("desc").toString());
                entity.setDiscount(Float.parseFloat(list.get(i).get("discount").toString()));
                entity.setConsumption(Float.parseFloat(list.get(i).get("consumption").toString()));
                entity.setAccumulation(Integer.parseInt(list.get(i).get("accumulation").toString()));
                entity.setStartTime(list.get(i).get("startTime").toString());
                entity.setEndTime(list.get(i).get("endTime").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
