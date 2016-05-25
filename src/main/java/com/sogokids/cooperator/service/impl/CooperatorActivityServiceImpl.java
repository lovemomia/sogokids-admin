package com.sogokids.cooperator.service.impl;

import com.sogokids.cooperator.model.ActivityEntry;
import com.sogokids.cooperator.model.CooperatorActivity;
import com.sogokids.cooperator.service.ActivityEntryService;
import com.sogokids.cooperator.service.CooperatorActivityService;
import com.sogokids.cooperator.service.CooperatorService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/3/31.
 */
@Service
public class CooperatorActivityServiceImpl implements CooperatorActivityService {

    @Autowired
    private ActivityEntryService activityEntryService;

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
    public CooperatorActivity get(int id) {
        String sql = "select Id, Icon, Cover, Title, `Desc`, Detail, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, ForNewUser, Stock, UnlockedStock, LockedStock, Status, AddTime from SG_Activity where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CooperatorActivity entity = new CooperatorActivity();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setIcon(rs.getString("Icon"));
                entity.setCover(rs.getString("Cover"));
                entity.setTitle(rs.getString("Title"));
                entity.setDesc(rs.getString("Desc"));
                entity.setDetail(rs.getString("Detail").replace(".jpg","_m.jpg"));
                entity.setMessage(rs.getString("Message"));
                entity.setNeedPay(rs.getInt("NeedPay"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setStock(rs.getInt("Stock"));
                entity.setUnlockedStock(rs.getInt("UnlockedStock"));
                entity.setLockedStock(rs.getInt("LockedStock"));
                entity.setForNewUser(rs.getInt("ForNewUser"));
                entity.setCooperatorId(rs.getInt("CooperatorId"));
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
    public List<CooperatorActivity> getEntitys() {
        List<CooperatorActivity> reData = new ArrayList<CooperatorActivity>();
        String sql = "select Id, Icon, Cover, Title, `Desc`, Detail, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, ForNewUser, Stock, UnlockedStock, LockedStock, Status, AddTime from SG_Activity where Status > ? order by StartTime desc ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            try {
                for (int i = 0; i < list.size(); i++) {
                    CooperatorActivity entity = new CooperatorActivity();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setCover(list.get(i).get("Cover").toString());
                    entity.setTitle(list.get(i).get("Title").toString());
                    entity.setDesc(list.get(i).get("Desc").toString());
                    entity.setMessage(list.get(i).get("Message") == null ? "" : list.get(i).get("Message").toString());
                    entity.setNeedPay(Integer.parseInt(list.get(i).get("NeedPay").toString()));
                    entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                    entity.setCooperatorId(Integer.parseInt(list.get(i).get("CooperatorId").toString()));
                    entity.setCooperator(cooperatorService.get(entity.getCooperatorId()));

                    entity.setStartTime(list.get(i).get("StartTime") == null ? "" : list.get(i).get("StartTime").toString());
                    entity.setEndTime(list.get(i).get("EndTime") == null ? "" : list.get(i).get("EndTime").toString());
                    entity.setOnlineTime(list.get(i).get("OnlineTime") == null ? "" : list.get(i).get("OnlineTime").toString());
                    entity.setOfflineTime(list.get(i).get("OfflineTime") == null ? "" : list.get(i).get("OfflineTime").toString());

                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime") == null ? "" : list.get(i).get("AddTime").toString());
                    entity.setSum_count(activityEntryService.getActivityEntrys(Integer.parseInt(list.get(i).get("Id").toString())).size());
                    reData.add(entity);
                }
            }catch (Exception _ex){
                _ex.printStackTrace();
            }
        }

        return reData;
    }

    @Override
    public List<CooperatorActivity> getEntitysByCoopId(int coopId) {
        List<CooperatorActivity> reData = new ArrayList<CooperatorActivity>();
        String sql = "select Id, Cover, Title, `Desc`, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, Status, AddTime from SG_Activity where Status > ? and CooperatorId = ? order by StartTime desc ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,coopId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            try {
                for (int i = 0; i < list.size(); i++) {
                    CooperatorActivity entity = new CooperatorActivity();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setCover(list.get(i).get("Cover").toString());
                    entity.setTitle(list.get(i).get("Title").toString());
                    entity.setDesc(list.get(i).get("Desc").toString());
                    entity.setMessage(list.get(i).get("Message") == null ? "" : list.get(i).get("Message").toString());
                    entity.setNeedPay(Integer.parseInt(list.get(i).get("NeedPay").toString()));
                    entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                    entity.setCooperatorId(Integer.parseInt(list.get(i).get("CooperatorId").toString()));
                    entity.setCooperator(cooperatorService.get(entity.getCooperatorId()));

                    entity.setStartTime(list.get(i).get("StartTime") == null ? "" : list.get(i).get("StartTime").toString());
                    entity.setEndTime(list.get(i).get("EndTime") == null ? "" : list.get(i).get("EndTime").toString());
                    entity.setOnlineTime(list.get(i).get("OnlineTime") == null ? "" : list.get(i).get("OnlineTime").toString());
                    entity.setOfflineTime(list.get(i).get("OfflineTime") == null ? "" : list.get(i).get("OfflineTime").toString());

                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                    entity.setAddTime(list.get(i).get("AddTime") == null ? "" : list.get(i).get("AddTime").toString());
                    entity.setSum_count(activityEntryService.getActivityEntrys(Integer.parseInt(list.get(i).get("Id").toString())).size());
                    reData.add(entity);
                }
            }catch (Exception _ex){
                _ex.printStackTrace();
            }
        }

        return reData;
    }

    //select Id, Icon, Cover, Title, `Desc`, Detail, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, ForNewUser, Stock, UnlockedStock, LockedStock, Status, AddTime from SG_Activity

    @Override
    public int insert(CooperatorActivity entity) {
        String sql = "insert into SG_Activity(Icon, Cover, Title, `Desc`, Detail, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, ForNewUser, Stock, UnlockedStock, Status, AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getIcon(), entity.getCover(), entity.getTitle(), entity.getDesc(), entity.getDetail(), entity.getMessage(), entity.getNeedPay(), entity.getPrice(), entity.getCooperatorId(), entity.getStartTime(), entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), entity.getForNewUser(), entity.getStock(), entity.getUnlockedStock(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(CooperatorActivity entity) {
        String sql = "update SG_Activity set Icon = ?, Cover = ?, Title = ?, `Desc` = ?, Detail = ?, Message = ?, NeedPay = ?, Price = ?, CooperatorId = ?, StartTime = ?, EndTime = ?, OnlineTime = ?, OfflineTime = ?, ForNewUser = ?, Stock = ?, UnlockedStock = ?  where Id = ? ";
        Object [] params = new Object[]{entity.getIcon(), entity.getCover(), entity.getTitle(), entity.getDesc(), entity.getDetail(), entity.getMessage(), entity.getNeedPay(), entity.getPrice(), entity.getCooperatorId(), entity.getStartTime(), entity.getEndTime(), entity.getOnlineTime(), entity.getOfflineTime(), entity.getForNewUser(), entity.getStock(), entity.getUnlockedStock(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Activity set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CooperatorActivity formEntity(HttpServletRequest request, int id) {
        CooperatorActivity entity = new CooperatorActivity();
        entity.setId(id);
        entity.setIcon(StringUtils.isEmpty(request.getParameter("icon"))?"":request.getParameter("icon"));
        entity.setCover(StringUtils.isEmpty(request.getParameter("cover"))?"":request.getParameter("cover"));
        entity.setTitle(StringUtils.isEmpty(request.getParameter("title")) ? "" : request.getParameter("title"));
        entity.setDesc(StringUtils.isEmpty(request.getParameter("desc")) ? "" : request.getParameter("desc"));
        String detail = StringUtils.isEmpty(request.getParameter("content")) ? "" : request.getParameter("content");
        detail = detail.replace("_m.jpg",".jpg");
        entity.setDetail(detail);
        entity.setMessage(StringUtils.isEmpty(request.getParameter("message")) ? "" : request.getParameter("message"));
        entity.setNeedPay(Integer.parseInt(request.getParameter("needPay")));
        entity.setPrice(new BigDecimal(StringUtils.isEmpty(request.getParameter("price")) ? "0" : request.getParameter("price")));
        entity.setCooperatorId(Integer.parseInt(request.getParameter("cooperatorId")));

        String time_null_value = "0000-00-00 00:00:00";
        entity.setStartTime(StringUtils.isEmpty(request.getParameter("startTime")) ? time_null_value : request.getParameter("startTime"));
        entity.setEndTime(StringUtils.isEmpty(request.getParameter("endTime")) ? time_null_value : request.getParameter("endTime"));
        entity.setOnlineTime(StringUtils.isEmpty(request.getParameter("onlineTime")) ? time_null_value : request.getParameter("onlineTime"));
        entity.setOfflineTime(StringUtils.isEmpty(request.getParameter("offlineTime")) ? time_null_value : request.getParameter("offlineTime"));

        int forNewUser = Integer.parseInt(StringUtils.isEmpty(request.getParameter("forNewUser")) ? "0" : "1");
        entity.setForNewUser(forNewUser);

        int stock = Integer.parseInt(StringUtils.isEmpty(request.getParameter("stock")) ? "0" : request.getParameter("stock"));

        if (id > 0){
            CooperatorActivity cooperatorActivity = this.get(id);
            int old_stock = cooperatorActivity.getStock();
            int old_unlockedStock = cooperatorActivity.getUnlockedStock();
            int stock_old_c = stock - old_stock;
            int unlockedStock = old_unlockedStock + stock_old_c;
            entity.setStock(stock);
            entity.setUnlockedStock(unlockedStock);
        }else{
            entity.setStock(stock);
            entity.setUnlockedStock(stock);
        }

        return entity;
    }

    @Override
    public CooperatorActivity getActivityEntrys(int id) {
        String sql = "select Id, Cover, Title, `Desc`, Message, NeedPay, Price, CooperatorId, StartTime, EndTime, OnlineTime, OfflineTime, Status, AddTime from SG_Activity where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CooperatorActivity entity = new CooperatorActivity();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setCover(rs.getString("Cover"));
                entity.setTitle(rs.getString("Title"));
                entity.setDesc(rs.getString("Desc"));
                entity.setMessage(rs.getString("Message"));
                entity.setNeedPay(rs.getInt("NeedPay"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setCooperatorId(rs.getInt("CooperatorId"));
                entity.setStartTime(DateUtil.DateToStr_Cn(rs.getString("StartTime")));
                entity.setEndTime(rs.getString("EndTime"));
                entity.setOnlineTime(rs.getString("OnlineTime"));
                entity.setOfflineTime(rs.getString("OfflineTime"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
                List<ActivityEntry> activityEntries = activityEntryService.getActivityEntrys(rs.getInt("Id"));
                entity.setActivityEntries(activityEntries);
                entity.setSum_count(activityEntries.size());
            }
        });

        return entity;
    }

    @Override
    public boolean isUnlockedStock(int id,int stock){
        boolean isTrue = true;

        CooperatorActivity cooperatorActivity = this.get(id);

        int old_stock = cooperatorActivity.getStock();
        int old_unlockedStock = cooperatorActivity.getUnlockedStock();
        int stock_old_c = stock - old_stock;
        int unlockedStock = old_unlockedStock + stock_old_c;
        if (unlockedStock < 0){
            isTrue = false;
        }

        return isTrue;
    }
}
