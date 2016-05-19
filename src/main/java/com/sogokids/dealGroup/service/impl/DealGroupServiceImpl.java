package com.sogokids.dealGroup.service.impl;

import com.sogokids.dealGroup.model.DealGroup;
import com.sogokids.dealGroup.model.DealGroupSubject;
import com.sogokids.dealGroup.service.DealGroupService;
import com.sogokids.dealGroup.service.DealGroupSubjectService;
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
public class DealGroupServiceImpl implements DealGroupService {

    private final Logger log = LoggerFactory.getLogger(DealGroupServiceImpl.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DealGroupSubjectService dealGroupSubjectService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DealGroup> getEntitys() {
        List<DealGroup> reData = new ArrayList<DealGroup>();
        String sql = "select Id,OwnerId,DealGroupSubjectId,MaxJoinCount,StartTime,EndTime,Status,AddTime from SG_DealGroup where Status > ? order by AddTime desc ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                DealGroup entity = new DealGroup();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                int ownerId = Integer.parseInt(list.get(i).get("OwnerId").toString());
                entity.setOwnerId(ownerId);
                entity.setCustomer(customerService.getCustomer(ownerId));
                int dealSubjectId = Integer.parseInt(list.get(i).get("DealGroupSubjectId").toString());
                entity.setDealGroupSubjectId(dealSubjectId);
                entity.setDealGroupSubject(dealGroupSubjectService.get(dealSubjectId));
                entity.setMaxJoinCount(Integer.parseInt(list.get(i).get("MaxJoinCount").toString()));
                entity.setStartTime(list.get(i).get("StartTime") == null?"":list.get(i).get("StartTime").toString());
                entity.setEndTime(list.get(i).get("EndTime") == null?"":list.get(i).get("EndTime").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }
}
