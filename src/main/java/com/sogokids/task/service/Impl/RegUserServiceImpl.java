package com.sogokids.task.service.Impl;

import com.sogokids.task.model.RegUser;
import com.sogokids.task.service.RegUserService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/11.
 */
@Service
public class RegUserServiceImpl implements RegUserService {

    private final Logger log = LoggerFactory.getLogger(RegUserServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insert(RegUser entity) {
        String sql = "insert into SG_ReportRegUser(RegDate, RegNumber, status,addTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getRegDate(), entity.getRegNumber(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            log.error("添加统计注册用户->error:"+_ex.getMessage());
        }
        return reData;
    }


    @Override
    public RegUser getDateRegUser(){
        RegUser entity = new RegUser();
        String before_day = DateUtil.getDatePreDay();
        String min_date = before_day+" 00:00:00";
        String max_date = before_day+" 23:59:59";
        String sql = "select * from SG_User where status > ? and addTime <= ? and addTime >= ?";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,max_date,min_date};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        int number = list.size();
        entity.setRegDate(before_day);
        entity.setRegNumber(number);

        return entity;
    }

    @Override
    public List<RegUser> getEntitys() {
        List<RegUser> reData = new ArrayList<RegUser>();
        String d_date = DateUtil.NowDateW()+" 00:00:00";
        String q_date = DateUtil.getDate7Day()+" 00:00:00";

        String sql = "select id,RegDate,RegNumber,status,addTime from SG_ReportRegUser where status > ? and RegDate < ? and RegDate >= ?";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,d_date,q_date};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                RegUser entity = new RegUser();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setRegDate(list.get(i).get("RegDate").toString());
                entity.setRegNumber(Integer.parseInt(list.get(i).get("RegNumber").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Map getregUsers(){
        List<RegUser> regUsers = this.getEntitys();
        StringBuffer x_date = new StringBuffer();
        StringBuffer y_number = new StringBuffer();
        x_date.append("[");
        y_number.append("[");
        for (RegUser regUser : regUsers){
            x_date.append("\""+DateUtil.getDateStr_en(regUser.getRegDate())+"\"").append(",");
            y_number.append(regUser.getRegNumber()).append(",");
        }
        x_date.append("]");
        y_number.append("]");

        Map map = new HashMap();
        map.put("user_date",x_date);
        map.put("user_number",y_number);

        return map;
    }

    @Override
    public int getAllUserCount(){
        int number = 0;

        String sql = "select * from SG_User where status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);

        number = list.size();

        return number;
    }
}
