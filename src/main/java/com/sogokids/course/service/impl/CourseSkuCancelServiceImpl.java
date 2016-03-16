package com.sogokids.course.service.impl;

import com.sogokids.course.model.CourseSkuCancel;
import com.sogokids.course.service.CourseSkuCancelService;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hoze on 16/3/7.
 */
@Service
public class CourseSkuCancelServiceImpl implements CourseSkuCancelService {

    private final Logger log = LoggerFactory.getLogger(CourseSkuCancelServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(CourseSkuCancel entity) {
        String sql = "insert into SG_CourseSkuCancel(SkuId,CancelReason,Status,AddTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getSkuId(), entity.getCancelReason(), Quantity.STATUS_ONE};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch(Exception _ex){
            log.error("CourseSkuCancelServiceImpl -> insert -> error info:"+_ex.getMessage());
        }
        return reData;
    }
}
