package com.sogokids.dealGroup.service.impl;

import com.sogokids.dealGroup.model.DealGroup;
import com.sogokids.dealGroup.model.DealGroupJoined;
import com.sogokids.dealGroup.service.DealGroupJoinedService;
import com.sogokids.query.service.CustomerService;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/21.
 */
@Service
public class DealGroupJoinedServiceImpl implements DealGroupJoinedService {

    private final Logger log = LoggerFactory.getLogger(DealGroupJoinedServiceImpl.class);

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
    public List<DealGroupJoined> getJoinsByGroupId(int group_id) {
        List<DealGroupJoined> reData = new ArrayList<DealGroupJoined>();
        String sql = "select Id,DealGroupId,UserId,RefundId,Status,AddTime from SG_DealGroupJoined where Status > ? and DealGroupId = ? order by AddTime desc ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,group_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                DealGroupJoined entity = new DealGroupJoined();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int dealGroupId = Integer.parseInt(list.get(i).get("DealGroupId").toString());
                entity.setDealGroupId(dealGroupId);
                int userId = Integer.parseInt(list.get(i).get("UserId").toString());
                entity.setUserId(userId);
                entity.setCustomer(customerService.getCustomer(userId));
                entity.setRefundId(Integer.parseInt(list.get(i).get("RefundId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }
}
