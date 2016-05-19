package com.sogokids.dealGroup.service.impl;

import com.alibaba.fastjson.JSON;
import com.sogokids.dealGroup.model.DealGroupSubject;
import com.sogokids.dealGroup.service.DealGroupSubjectService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by hoze on 16/4/21.
 */
@Service
public class DealGroupSubjectServiceImpl implements DealGroupSubjectService {

    private final Logger log = LoggerFactory.getLogger(DealGroupSubjectServiceImpl.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DealGroupSubject> getEntitys() {
        List<DealGroupSubject> reData = new ArrayList<DealGroupSubject>();
        String sql = "select Id,SubjectId,SubjectSkuId,Title,MaxJoinCount,Days,Status,AddTime from SG_DealGroupSubject where Status > ? order by AddTime desc ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                DealGroupSubject entity = new DealGroupSubject();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setSubjectSkuId(Integer.parseInt(list.get(i).get("SubjectSkuId").toString()));
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setMaxJoinCount(Integer.parseInt(list.get(i).get("MaxJoinCount").toString()));
                entity.setDays(Integer.parseInt(list.get(i).get("Days").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public DealGroupSubject get(int id) {
        String sql = "select Id,SubjectId,SubjectSkuId,Title,MaxJoinCount,Days,Status,AddTime from SG_DealGroupSubject where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final DealGroupSubject entity = new DealGroupSubject();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("Id"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setSubjectSkuId(rs.getInt("SubjectSkuId"));
                entity.setTitle(rs.getString("Title"));
                entity.setMaxJoinCount(rs.getInt("MaxJoinCount"));
                entity.setDays(rs.getInt("Days"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(DealGroupSubject entity) {
        String sql = "insert into SG_DealGroupSubject(SubjectId,SubjectSkuId,Title,MaxJoinCount,Days,Status,AddTime) value(?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getSubjectSkuId(), entity.getTitle(), entity.getMaxJoinCount(), entity.getDays(), Quantity.STATUS_TWO};
        int reData = 0;
        try{
            log.info("开始添加拼团信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束添加拼团信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(DealGroupSubject entity) {
        String sql = "update SG_DealGroupSubject set SubjectId = ?, SubjectSkuId = ?, Title = ?, MaxJoinCount = ?, Days = ? where Id = ? ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getSubjectSkuId(), entity.getTitle(), entity.getMaxJoinCount(), entity.getDays(), entity.getId()};
        int reData = 0;
        try{
            log.info("开始修改拼团信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束修改拼团信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_DealGroupSubject set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        log.info("开始删除拼团信息!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束删除拼团信息!");
        return reData;
    }

    @Override
    public int updateStatus(int id,int mark) {
        String sql = "update SG_DealGroupSubject set Status = ? where Id = ? ";
        Object [] params = new Object[]{mark,id};
        log.info("开始拼团上下线操作!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束拼团上下线操作!");
        return reData;
    }

    @Override
    public DealGroupSubject formEntity(HttpServletRequest request, int id) {
        DealGroupSubject entity = new DealGroupSubject();

        entity.setId(id);
        entity.setSubjectId(Integer.parseInt(request.getParameter("subjectId")));
        entity.setSubjectSkuId(Integer.parseInt(request.getParameter("subjectSkuId")));
        entity.setTitle(request.getParameter("title"));
        entity.setMaxJoinCount(Integer.parseInt(request.getParameter("maxJoinCount")));
        entity.setDays(Integer.parseInt(request.getParameter("days")));

        return entity;
    }

    @Override
    public String getSubjectSkuJsonStr(int deal_id){
        List<Subject> subjects = new ArrayList<Subject>();
        if (deal_id == 0){
            List<Subject> sub_ls = subjectService.getModelsByType();
            if (sub_ls.size() > 0){
                for (int i = 0; i < sub_ls.size(); i++) {
                    Subject subject = sub_ls.get(i);
                    List<SubjectSku> subjectSkus = subjectSkuService.getModelsBySub_Key(subject.getId());
                    for (SubjectSku subjectSku : subjectSkus) {
                        subjectSku.setLd_name(this.getLd_name(subjectSku));
                    }
                    subject.setSubjectSkus(subjectSkus);
                    subjects.add(subject);
                }
            }
        }else{
            DealGroupSubject dealGroupSubject = this.get(deal_id);
            SubjectSku subjectSku = subjectSkuService.get(dealGroupSubject.getSubjectSkuId());
            List<Subject> sub_ls = subjectService.getModelsByType();
            if (sub_ls.size() > 0){
                for (int i = 0; i < sub_ls.size(); i++) {
                    Subject subject = sub_ls.get(i);
                    if (subject.getId() == subjectSku.getSubjectId()){
                        subject.setSelected(1);
                    }
                    List<SubjectSku> sub_sku_ls = new ArrayList<SubjectSku>();
                    List<SubjectSku> subjectSkus1 = subjectSkuService.getModelsBySub_Key(subject.getId());
                    for (int j = 0; j < subjectSkus1.size(); j++) {
                        SubjectSku entity = subjectSkus1.get(j);
                        entity.setLd_name(this.getLd_name(entity));
                        if (entity.getId() == subjectSku.getId() && subject.getId() == entity.getSubjectId()){
                            entity.setSelected(1);
                        }
                        sub_sku_ls.add(entity);
                    }
                    subject.setSubjectSkus(sub_sku_ls);
                    subjects.add(subject);
                }
            }


        }

        String reData = JSON.toJSONString(subjects);

        return reData;
    }

    private String getLd_name(SubjectSku subjectSku){
        int timeUnit = subjectSku.getTimeUnit();
        StringBuffer sb = new StringBuffer();
        String unitName = "";
        if (timeUnit == 3){
            unitName = "年";
        }else if(timeUnit == 2){
            unitName = "季度";
        }else{
            unitName = "月";
        }
        sb.append(subjectSku.getPrice()).append("元 ").append(subjectSku.getTime()).append(unitName+" ").append(subjectSku.getCourseCount()).append("节课");

        return sb.toString();
    }
}
