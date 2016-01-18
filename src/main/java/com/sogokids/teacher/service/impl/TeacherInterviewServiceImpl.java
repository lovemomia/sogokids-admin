package com.sogokids.teacher.service.impl;

import com.sogokids.teacher.model.Teacher;
import com.sogokids.teacher.model.TeacherInterview;
import com.sogokids.teacher.service.TeacherInterviewService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hoze on 16/1/12.
 */
@Service
public class TeacherInterviewServiceImpl implements TeacherInterviewService {

    private final Logger log = LoggerFactory.getLogger(TeacherInterviewServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TeacherInterview getTeacherInterviews(int userId){
        String sql = "select Id,UserId,InterviewDate,Address,Status,AddTime from SG_TeacherInterview where UserId = ? and Status > ? ";
        final Object [] params = new Object[]{userId, Quantity.STATUS_ZERO};
        final TeacherInterview entity = new TeacherInterview();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUserId(rs.getInt("UserId"));
                entity.setInterviewDate(DateUtil.DateToStr_Cn(rs.getString("InterviewDate")));
                entity.setAddress(rs.getString("Address"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(TeacherInterview entity) {
        String sql = "insert into SG_TeacherInterview(UserId,InterviewDate,Address,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getUserId(), entity.getInterviewDate(), entity.getAddress(), Quantity.STATUS_ONE};
        int reData = 0;
        try {
            reData = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            log.error("error info:" + _ex.getMessage());
        }
        return reData;
    }

    @Override
    public TeacherInterview formEntity(HttpServletRequest request, int userId) {
        TeacherInterview entity = new TeacherInterview();

        entity.setUserId(userId);
        entity.setInterviewDate(request.getParameter("dateTime"));
        entity.setAddress(request.getParameter("address"));

        return entity;
    }
}
