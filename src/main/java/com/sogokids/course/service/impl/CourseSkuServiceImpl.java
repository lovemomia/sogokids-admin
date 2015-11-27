package com.sogokids.course.service.impl;

import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.system.model.Place;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.util.Quantity;
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
 * Created by hoze on 15/10/12.
 */
@Service
public class CourseSkuServiceImpl implements CourseSkuService {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CourseService courseService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<CourseSku> getEntitys() {
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseSku entity = new CourseSku();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setStartTime(list.get(i).get("StartTime").toString());
                entity.setEndTime(list.get(i).get("EndTime").toString());
                entity.setDeadline(list.get(i).get("Deadline").toString());
                entity.setStock(Integer.parseInt(list.get(i).get("Stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("UnlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("LockedStock").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("PlaceId").toString()));
                entity.setAdult(Integer.parseInt(list.get(i).get("Adult").toString()));
                entity.setChild(Integer.parseInt(list.get(i).get("Child").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setPlaceName(placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString())).getAddress());
                entity.setCourseName(courseService.get(Integer.parseInt(list.get(i).get("CourseId").toString())).getTitle());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseSku> getModelsByCourse_Key(int course_id) {
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where CourseId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseSku entity = new CourseSku();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setStartTime(list.get(i).get("StartTime").toString());
                entity.setEndTime(list.get(i).get("EndTime").toString());
                entity.setDeadline(list.get(i).get("Deadline").toString());
                entity.setStock(Integer.parseInt(list.get(i).get("Stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("UnlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("LockedStock").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("PlaceId").toString()));
                entity.setAdult(Integer.parseInt(list.get(i).get("Adult").toString()));
                entity.setChild(Integer.parseInt(list.get(i).get("Child").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setPlaceName(placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString())).getAddress());
                entity.setCourseName(courseService.get(Integer.parseInt(list.get(i).get("CourseId").toString())).getTitle());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<CourseSku> getCourseSkuById(int id) {
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where ParentId = ? and Status > ? ";
        Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseSku entity = new CourseSku();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setStartTime(list.get(i).get("StartTime").toString());
                entity.setEndTime(list.get(i).get("EndTime").toString());
                entity.setDeadline(list.get(i).get("Deadline").toString());
                entity.setStock(Integer.parseInt(list.get(i).get("Stock").toString()));
                entity.setUnlockedStock(Integer.parseInt(list.get(i).get("UnlockedStock").toString()));
                entity.setLockedStock(Integer.parseInt(list.get(i).get("LockedStock").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("PlaceId").toString()));
                entity.setAdult(Integer.parseInt(list.get(i).get("Adult").toString()));
                entity.setChild(Integer.parseInt(list.get(i).get("Child").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setPlaceName(placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString())).getAddress());
                entity.setCourseName(courseService.get(Integer.parseInt(list.get(i).get("CourseId").toString())).getTitle());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public CourseSku get(int id) {
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final CourseSku entity = new CourseSku();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setParentId(rs.getInt("ParentId"));
                entity.setStartTime(rs.getString("StartTime"));
                entity.setEndTime(rs.getString("EndTime"));
                entity.setDeadline(rs.getString("Deadline"));
                entity.setStock(rs.getInt("Stock"));
                entity.setUnlockedStock(rs.getInt("UnlockedStock"));
                entity.setLockedStock(rs.getInt("LockedStock"));
                entity.setPlaceId(rs.getInt("PlaceId"));
                entity.setAdult(rs.getInt("Adult"));
                entity.setChild(rs.getInt("Child"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

            }
        });

        return entity;
    }

    @Override
    public int insert(CourseSku entity) {
        String sql = "insert into SG_CourseSku(CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getParentId(), entity.getStartTime(), entity.getEndTime(), entity.getDeadline(), entity.getStock(), entity.getUnlockedStock(), entity.getLockedStock(), entity.getPlaceId(), entity.getAdult(), entity.getChild(), Quantity.STATUS_ONE};
        int reDate = 0;
        try {
            reDate = jdbcTemplate.update(sql, params);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reDate;
    }

    @Override
    public int update(CourseSku entity) {
        String sql = "update SG_CourseSku set CourseId = ?, StartTime = ?, EndTime = ?, Deadline = ?, Stock = ?, UnlockedStock = ?, LockedStock = ?, PlaceId = ?, Adult = ?, Child = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getStartTime(), entity.getEndTime(), entity.getDeadline(), entity.getStock(), entity.getUnlockedStock(), entity.getLockedStock(), entity.getPlaceId(), entity.getAdult(), entity.getChild(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_CourseSku set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public CourseSku formEntity(HttpServletRequest request, int course_id, int id) {
        CourseSku entity = new CourseSku();

        entity.setId(id);
        entity.setCourseId(course_id);
        entity.setParentId(Quantity.STATUS_ZERO);
        entity.setStartTime(request.getParameter("startTime"));
        entity.setEndTime(request.getParameter("endTime"));
        entity.setDeadline(request.getParameter("deadline"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        entity.setStock(stock);
        if (id > 0) {
            CourseSku cs = this.get(id);
            int unlockedStock = stock - cs.getLockedStock();
            entity.setUnlockedStock(unlockedStock);
            entity.setLockedStock(cs.getLockedStock());
        }else{
            entity.setUnlockedStock(stock);
            entity.setLockedStock(0);
        }

        entity.setPlaceId(Integer.parseInt(request.getParameter("placeId")));
        entity.setAdult(Integer.parseInt(request.getParameter("adult")));
        entity.setChild(Integer.parseInt(request.getParameter("child")));

        return entity;
    }

    @Override
    public String getSkuHtml(int course_id) {
        StringBuffer sb = new StringBuffer();
        String p_start = "<p>";
        String p_end = "</p>";
        List<CourseSku> course_sku = this.getModelsByCourse_Key(course_id);
        if (course_sku.size() > 0){

            for (CourseSku sku : course_sku){
                sb.append(p_start);
                sb.append("地址详情:"+placeService.get(sku.getPlaceId()).getAddress()+"<br>");
                sb.append("成人:"+sku.getAdult()+"<br>");
                sb.append("儿童:"+sku.getChild()+"<br>");
                sb.append("开始时间:"+sku.getStartTime()+"<br>");
                sb.append("结束时间:"+sku.getEndTime()+"<br>");
                sb.append("下线时间:"+sku.getDeadline()+"<br>");
                sb.append("库存:"+sku.getStock()+"<br>");

                String sku_value = "'"+sku.getId()+"'";
                sb.append("<a class='btn btn-success btn-sm' href='javascript:void(0)' onclick=\"skuEdit(" + sku_value + ")\"><i class='fa fa-pencil'></i>编辑</a>");
//                sb.append("&nbsp;&nbsp;");
//                sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"skuDel(" + sku_value + ")\"><i class='fa fa-trash'></i>删除</a>");
                sb.append(p_end);
                sb.append("<div class='hr-line-dashed'></div>");
            }

        }

        return sb.toString();
    }

    @Override
    public String getSkuPlaceHtml(int cs_id) {
        StringBuffer sb = new StringBuffer();
        if (cs_id == 0){
            List<Place> places = placeService.getEntitys();
            sb.append("<select id='placeId' name='placeId' class='form-control m-b' style='width: 200px' >");
            if (places.size() > 0){
                for (int i = 0; i < places.size(); i++) {
                    sb.append("<option value='"+places.get(i).getId()+"'>"+places.get(i).getName()+"&nbsp;"+places.get(i).getAddress()+"</option>");
                }
            }
            sb.append("</select>");
        }else{
            CourseSku sku = this.get(cs_id);
            List<Place> places = placeService.getEntitys();
            sb.append("<select id='placeId' name='placeId' class='form-control m-b' style='width: 200px' >");
            if (places.size() > 0){
                for (int i = 0; i < places.size(); i++) {
                    if (sku.getPlaceId() == places.get(i).getId()){
                        sb.append("<option value='"+places.get(i).getId()+"' selected>"+places.get(i).getName()+"&nbsp;"+places.get(i).getAddress()+"</option>");
                    }else{
                        sb.append("<option value='"+places.get(i).getId()+"'>"+places.get(i).getName()+"&nbsp;"+places.get(i).getAddress()+"</option>");
                    }
                }
            }
            sb.append("</select>");
        }

        return sb.toString();
    }

    @Override
    public String getCopySkuHtml(int course_id) {
        StringBuffer sb = new StringBuffer();
        String table_start = "<table class=\"table table-striped table-bordered table-hover dataTables-example\">";
        String table_end = "</table>";
        List<CourseSku> course_sku = this.getModelsByCourse_Key(course_id);
        if (course_sku.size() > 0){
            sb.append(table_start);
            for (CourseSku sku : course_sku){
                if (sku.getUnlockedStock() != 0) {
                    String input_id = "stock_"+sku.getId();
                    sb.append("<tr>");
                    sb.append("<td><input name='course_sku_id' type='checkbox' value='"+sku.getId()+"'></td>");
                    sb.append("<td>");

                    sb.append("开始时间:"+sku.getStartTime().substring(0,19));
                    sb.append("<br>");
                    sb.append("活动地址:"+sku.getPlaceName());
                    sb.append("<br>");
                    sb.append("参加人员:"+sku.getAdult()+"成人;"+sku.getChild()+"儿童;");
                    sb.append("</td>");

                    sb.append("<td>");
                    sb.append("总库存:"+sku.getStock()+";");
                    sb.append("<br>");
                    sb.append("剩余库存:"+sku.getUnlockedStock()+";");
                    sb.append("<br>");
                    List<CourseSku> courseSkus = this.getCourseSkuById(sku.getId());
                    if (courseSkus.size() > 0) {
                        for (int i = 0; i < courseSkus.size(); i++) {
                            CourseSku courseSku = courseSkus.get(i);
                            sb.append("<input id='" + input_id + "' name='" + input_id + "' value='"+courseSku.getStock()+"' >");
                        }
                    }else{
                        sb.append("<input id='" + input_id + "' name='" + input_id + "' >");
                    }
                    sb.append("</td>");
                    sb.append("</tr>");
                }
            }
            sb.append(table_end);

        }

        return sb.toString();
    }
}
