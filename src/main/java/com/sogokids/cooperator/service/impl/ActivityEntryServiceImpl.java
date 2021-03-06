package com.sogokids.cooperator.service.impl;

import com.sogokids.cooperator.model.ActivityEntry;
import com.sogokids.cooperator.service.ActivityEntryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/1.
 */
@Service
public class ActivityEntryServiceImpl implements ActivityEntryService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ActivityEntry> getActivityEntrys(int activityId) {
        List<ActivityEntry> reData = new ArrayList<ActivityEntry>();
        String sql = "SELECT a.Id,a.Mobile,a.ChildName,a.Status,b.Id as payId,b.PayType,b.FinishTime,b.Fee FROM (select Id,Mobile,ChildName,Status from sogokids.SG_ActivityEntry where status >= 3 and ActivityId = ? ) a left join sogokids.SG_ActivityPayment b on a.Id = b.OrderId and b.status > 0 order by b.FinishTime desc";
        Object [] params = new Object[]{activityId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            try {
                for (int i = 0; i < list.size(); i++) {
                    ActivityEntry entity = new ActivityEntry();
                    entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                    entity.setMobile(list.get(i).get("Mobile").toString());
                    entity.setChildName(list.get(i).get("ChildName").toString());
                    entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));

                    String payId = list.get(i).get("payId") == null ? "0":list.get(i).get("payId").toString();
                    entity.setPayId(Integer.parseInt(payId));
                    entity.setPayType(Integer.parseInt(list.get(i).get("PayType") == null ? "0":list.get(i).get("PayType").toString()));
                    entity.setFinishTime(list.get(i).get("FinishTime") == null ? "" : list.get(i).get("FinishTime").toString());
                    entity.setFee(new BigDecimal(list.get(i).get("Fee") == null ? "0":list.get(i).get("Fee").toString()));

                    reData.add(entity);
                }
            }catch (Exception _ex){
                _ex.printStackTrace();
            }
        }

        return reData;
    }
}
