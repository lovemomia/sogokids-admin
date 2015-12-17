package com.sogokids.subject.service.Impl;

import com.sogokids.home.model.Banner;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.model.SubjectNotice;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
@Service
public class SubjectSkuServiceImpl implements SubjectSkuService {

    @Autowired
    private SubjectService subjectService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<SubjectSku> getEntitys() {
        List<SubjectSku> reData = new ArrayList<SubjectSku>();
        String sql = "select Id,SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime from SG_SubjectSku where Status > ? and courseId = ? and Status != ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SubjectSku entity = new SubjectSku();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setDesc(list.get(i).get("Desc").toString());
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setOriginalPrice(new BigDecimal(list.get(i).get("OriginalPrice").toString()));
                entity.setAdult(Integer.parseInt(list.get(i).get("Adult").toString()));
                entity.setChild(Integer.parseInt(list.get(i).get("Child").toString()));
                entity.setCourseCount(Integer.parseInt(list.get(i).get("CourseCount").toString()));
                entity.setTime(Integer.parseInt(list.get(i).get("Time").toString()));
                entity.setTimeUnit(Integer.parseInt(list.get(i).get("TimeUnit").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<SubjectSku> getModelsBySub_Key(int sub_id) {
        List<SubjectSku> reData = new ArrayList<SubjectSku>();
        String sql = "select Id,SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime from SG_SubjectSku where SubjectId = ? and CourseId = ? and Status > ? and Status != ? ";
        Object [] params = new Object[]{sub_id, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SubjectSku entity = new SubjectSku();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setDesc(list.get(i).get("Desc").toString());
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setOriginalPrice(new BigDecimal(list.get(i).get("OriginalPrice").toString()));
                entity.setAdult(Integer.parseInt(list.get(i).get("Adult").toString()));
                entity.setChild(Integer.parseInt(list.get(i).get("Child").toString()));
                entity.setCourseCount(Integer.parseInt(list.get(i).get("CourseCount").toString()));
                entity.setTime(Integer.parseInt(list.get(i).get("Time").toString()));
                entity.setTimeUnit(Integer.parseInt(list.get(i).get("TimeUnit").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public SubjectSku getSubjectSku(int sub_id, int cour_id) {
        String sql = "select Id,SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime from SG_SubjectSku where SubjectId = ? and CourseId = ? and Status > ? and Status != ? ";
        final Object [] params = new Object[]{sub_id, cour_id, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        final SubjectSku entity = new SubjectSku();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setDesc(rs.getString("Desc"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setOriginalPrice(rs.getBigDecimal("OriginalPrice"));
                entity.setAdult(rs.getInt("Adult"));
                entity.setChild(rs.getInt("Child"));
                entity.setCourseCount(rs.getInt("CourseCount"));
                entity.setTimeUnit(rs.getInt("TimeUnit"));
                entity.setTime(rs.getInt("Time"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }


    @Override
    public SubjectSku get(int id) {
        String sql = "select Id,SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime from SG_SubjectSku where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final SubjectSku entity = new SubjectSku();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setSubjectId(rs.getInt("SubjectId"));
                entity.setDesc(rs.getString("Desc"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setOriginalPrice(rs.getBigDecimal("OriginalPrice"));
                entity.setAdult(rs.getInt("Adult"));
                entity.setChild(rs.getInt("Child"));
                entity.setCourseCount(rs.getInt("CourseCount"));
                entity.setTimeUnit(rs.getInt("TimeUnit"));
                entity.setTime(rs.getInt("Time"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(SubjectSku entity) {
        String sql = "insert into SG_SubjectSku(SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getDesc(), entity.getPrice(), entity.getOriginalPrice(), entity.getAdult(), entity.getChild(), entity.getCourseCount(), entity.getTime(), entity.getTimeUnit(), Quantity.STATUS_ONE};
        int reDate = 0;
        try {
            reDate = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reDate;
    }

    @Override
    public int insertOne(SubjectSku entity) {
        String sql = "insert into SG_SubjectSku(SubjectId,CourseId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,`Limit`,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getCourseId(), entity.getDesc(), entity.getPrice(), entity.getOriginalPrice(), entity.getAdult(), entity.getChild(), entity.getCourseCount(), entity.getTime(), entity.getTimeUnit(), entity.getLimit(), Quantity.STATUS_ONE};
        int reDate = 0;
        try {
            reDate = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reDate;
    }

    @Override
    public int update(SubjectSku entity) {
        String sql = "update SG_SubjectSku set SubjectId = ?, `Desc` = ?, Price = ?, OriginalPrice = ?, Adult = ?, Child = ?, CourseCount = ?, Time = ?, TimeUnit = ? where Id = ? ";
        Object [] params = new Object[]{entity.getSubjectId(), entity.getDesc(), entity.getPrice(), entity.getOriginalPrice(), entity.getAdult(), entity.getChild(), entity.getCourseCount(), entity.getTime(), entity.getTimeUnit(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_SubjectSku set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "update SG_SubjectSku set Status = ? where Id = ? ";
        Object [] params = new Object[]{status,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public SubjectSku formEntity(HttpServletRequest request, int sub_id, int id) {
        SubjectSku entity = new SubjectSku();

        entity.setId(id);
        entity.setSubjectId(sub_id);
        entity.setDesc(request.getParameter("desc"));
        entity.setPrice(new BigDecimal(request.getParameter("price")));
        entity.setOriginalPrice(new BigDecimal(request.getParameter("price")));
        entity.setAdult(Integer.parseInt(request.getParameter("adult")));
        entity.setChild(Integer.parseInt(request.getParameter("child")));
        String courseCount = request.getParameter("courseCount");
        if (courseCount == null || courseCount.equals("")){
            courseCount = "1";
        }
        entity.setCourseCount(Integer.parseInt(courseCount));
        entity.setTime(Integer.parseInt(request.getParameter("time")));
        entity.setTimeUnit(Integer.parseInt(request.getParameter("timeUnit")));

        return entity;
    }

    @Override
    public SubjectSku formEntityOne(HttpServletRequest request, int sub_id, int id) {
        SubjectSku entity = new SubjectSku();

        entity.setId(id);
        entity.setSubjectId(sub_id);
        entity.setDesc(request.getParameter("desc"));
        entity.setPrice(new BigDecimal(request.getParameter("price")));
        entity.setOriginalPrice(new BigDecimal(request.getParameter("price")));
        entity.setAdult(Integer.parseInt(request.getParameter("adult")));
        entity.setChild(Integer.parseInt(request.getParameter("child")));
        entity.setCourseCount(Quantity.STATUS_ONE);
        entity.setTime(Quantity.STATUS_ONE);
        entity.setTimeUnit(Quantity.STATUS_ONE);
        entity.setLimit(Quantity.STATUS_ZERO);

        return entity;
    }

    @Override
    public String getSkuHtml(int sub_id){
        StringBuffer sb = new StringBuffer();
        String p_start = "<p>";
        String p_end = "</p>";
        Subject subject = subjectService.get(sub_id);
        int status = subject.getStatus();
        List<SubjectSku> sub_sku = this.getModelsBySub_Key(sub_id);
        if (sub_sku.size() > 0){

            for (SubjectSku sku : sub_sku){
                sb.append(p_start);
                    sb.append("描述:"+sku.getDesc()+"<br>");
//                    sb.append("原价:"+sku.getOriginalPrice());
//                    sb.append("&nbsp;&nbsp;");
//                    sb.append("&nbsp;&nbsp;");
                    sb.append("售价:"+sku.getPrice()+"<br>");
                    sb.append("大人:"+sku.getAdult());
                    sb.append("&nbsp;&nbsp;");
                    sb.append("&nbsp;&nbsp;");
                    sb.append("小孩:"+sku.getChild()+"<br>");
                    sb.append("课程数:"+sku.getCourseCount());
                    sb.append("&nbsp;&nbsp;");
                    sb.append("&nbsp;&nbsp;");
                    int timeUnit = sku.getTimeUnit();
                    String timeUnitName = "年";
                    if (timeUnit == 1){
                        timeUnitName = "月";
                    }
                    if (timeUnit == 2){
                        timeUnitName = "季度";
                    }
                    sb.append("时间:"+sku.getTime()+timeUnitName+"<br>");

                    String sku_value = "'"+sku.getId()+"'";
                sb.append("<br>");
                if (status == 2) {
                    sb.append("<a class='btn btn-success btn-sm' href='javascript:void(0)' onclick=\"skuEdit(" + sku_value + ")\"><i class='fa fa-pencil'></i>编辑</a>");
                    sb.append("&nbsp;&nbsp;");
                    sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"skuDel(" + sku_value + ")\"><i class='fa fa-trash'></i>删除</a>");
                }else{
                    sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"cancelSku(" + sku_value + ")\"><i class='fa fa-minus-circle'></i>取消</a>");
                }
                sb.append(p_end);
                sb.append("<div class='hr-line-dashed'></div>");
            }

        }

        return sb.toString();
    }

    @Override
    public String getCourseSkuHtml(int course_id){
        StringBuffer sb = new StringBuffer();
        String p_start = "<p>";
        String p_end = "</p>";
        String sql = "select Id,SubjectId,`Desc`,Price,OriginalPrice,Adult,Child,CourseCount,Time,TimeUnit,Status,AddTime from SG_SubjectSku where courseId = ? and Status > ? and Status != ? ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){

            for (int i = 0; i < list.size(); i++) {
                sb.append(p_start);
                sb.append("描述:"+list.get(i).get("Desc")+"<br>");
                sb.append("售价:"+list.get(i).get("Price")+"<br>");
                sb.append("大人:"+list.get(i).get("Adult"));
                sb.append("&nbsp;&nbsp;");
                sb.append("&nbsp;&nbsp;");
                sb.append("小孩:"+list.get(i).get("Child")+"<br>");
                sb.append("<br>");

                String sku_value = "'"+Integer.parseInt(list.get(i).get("Id").toString())+"'";
                sb.append("<a class='btn btn-success btn-sm' href='javascript:void(0)' onclick=\"skuEdit(" + sku_value + ")\"><i class='fa fa-pencil'></i>编辑</a>");
                sb.append("&nbsp;&nbsp;");
//                sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"skuDel(" + sku_value + ")\"><i class='fa fa-trash'></i>删除</a>");
                sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"cancelSku(" + sku_value + ")\"><i class='fa fa-minus-circle'></i>取消</a>");
                sb.append(p_end);
                sb.append("<div class='hr-line-dashed'></div>");
            }

        }

        return sb.toString();
    }

}
