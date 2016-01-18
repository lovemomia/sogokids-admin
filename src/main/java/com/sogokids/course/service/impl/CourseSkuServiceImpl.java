package com.sogokids.course.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.order.model.OrderPackage;
import com.sogokids.order.service.OrderPackageService;
import com.sogokids.subject.model.Subject;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.system.model.Place;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoze on 15/10/12.
 */
@Service
public class CourseSkuServiceImpl implements CourseSkuService {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private OrderPackageService orderPackageService;


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
    public List<CourseSku> getEntitys() {
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where Status > ? and Status != ? order by StartTime desc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,Quantity.STATUS_THREE};
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
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where CourseId = ? and Status > ? and Status != ? order by StartTime desc ";
        Object [] params = new Object[]{course_id, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
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
    public List<CourseSku> getCourseSkuById(int course_id, int parentId) {
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String sql = "select Id,CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime from SG_CourseSku where CourseId = ? and ParentId = ? and Status > ? and Status != ? order by StartTime desc";
        Object [] params = new Object[]{course_id, parentId, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
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
    public int insertKey(CourseSku courseSku) {
        final CourseSku entity = courseSku;
        final String sql = "insert into SG_CourseSku(CourseId,ParentId,StartTime,EndTime,Deadline,Stock,UnlockedStock,LockedStock,PlaceId,Adult,Child,Status,AddTime) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = 0;
        try {
            reData = jdbcTemplate.update(new PreparedStatementCreator() {
                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                    int i = 0;
                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(++i, entity.getCourseId());
                    ps.setInt(++i, entity.getParentId());
                    ps.setString(++i, entity.getStartTime());
                    ps.setString(++i, entity.getEndTime());
                    ps.setString(++i, entity.getDeadline());
                    ps.setInt(++i, entity.getStock());
                    ps.setInt(++i, entity.getUnlockedStock());
                    ps.setInt(++i, entity.getLockedStock());
                    ps.setInt(++i, entity.getPlaceId());
                    ps.setInt(++i, entity.getAdult());
                    ps.setInt(++i, entity.getChild());
                    ps.setInt(++i, Quantity.STATUS_ONE);

                    return ps;
                }
            }, keyHolder);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;
    }

    @Override
    public int update(CourseSku entity) {
        String sql = "update SG_CourseSku set CourseId = ?, StartTime = ?, EndTime = ?, Deadline = ?, Stock = ?, UnlockedStock = ?, LockedStock = ?, PlaceId = ?, Adult = ?, Child = ? where Id = ? ";
        Object [] params = new Object[]{entity.getCourseId(), entity.getStartTime(), entity.getEndTime(), entity.getDeadline(), entity.getStock(), entity.getUnlockedStock(), entity.getLockedStock(), entity.getPlaceId(), entity.getAdult(), entity.getChild(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

//    @Override
    public int updateStatus(int id,int status) {
        String sql = "update SG_CourseSku set Status = ? where Id = ? ";
        Object [] params = new Object[]{status, id};
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
        entity.setDeadline(DateUtil.getDateDay(request.getParameter("startTime")));
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
        Course course = courseService.get(course_id);
        int status = course.getStatus();
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
                sb.append("报名截止:"+sku.getDeadline()+"<br>");
                sb.append("库存:"+sku.getStock()+"<br>");
                sb.append("<br>");
                String sku_value = "'"+sku.getId()+"'";
                if (status == 2) {
                    sb.append("<a class='btn btn-success btn-sm' href='javascript:void(0)' onclick=\"skuEdit(" + sku_value + ")\"><i class='fa fa-pencil'></i>编辑</a>");
                    sb.append("&nbsp;&nbsp;");
                    sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"skuDel(" + sku_value + ")\"><i class='fa fa-trash'></i>删除</a>");
//                    sb.append("&nbsp;&nbsp;");
//                    sb.append("<a class='btn btn-primary btn-sm' data-toggle=\"modal\" data-target=\"#myQz\" href='javascript:void(0)' onclick=\"createQz(" + sku_value + ")\"><i class='fa fa-user-plus'></i>创建群组</a>");
                }else{
//                    sb.append("<a class='btn btn-primary btn-sm' data-toggle=\"modal\" data-target=\"#myQz\" href='javascript:void(0)' onclick=\"createQz(" + sku_value + ")\"><i class='fa fa-user-plus'></i>创建群组</a>");
//                    sb.append("&nbsp;&nbsp;");
                    sb.append("<a class='btn btn-danger btn-sm' href='javascript:void(0)' onclick=\"cancelSku(" + sku_value + ")\"><i class='fa fa-minus-circle'></i>取消</a>");
                }
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
        String reStr = "";
        List<Course> courses = courseService.getEntitysById(course_id);
        if (courses.size() > 0){
            reStr = getCopySkuHtmlNotNull(course_id,courses.get(0).getId());
        }else{
            reStr = getCopySkuHtmlNull(course_id);
        }

        return reStr;
    }

    private String getCopySkuHtmlNotNull(int course_id_p, int course_id_c) {
        StringBuffer sb = new StringBuffer();
        String table_start = "<table class=\"table table-striped table-bordered table-hover dataTables-example\">";
        String table_end = "</table>";
        List<CourseSku> course_sku = this.getModelsByCourse_Key(course_id_p);
        if (course_sku.size() > 0){
            sb.append(table_start);
            for (CourseSku sku : course_sku){
                if (sku.getUnlockedStock() != 0) {
                    String input_id = "stock_"+sku.getId();
                    String input_hidden_id = "un_stock_"+sku.getId();
                    String y_input_id = "y_stock_"+sku.getId();
                    List<CourseSku> courseSkus = this.getCourseSkuById(course_id_c, sku.getId());
                    sb.append("<tr>");
                    if (courseSkus.size() > 0) {
                        sb.append("<td><input id='course_sku_id' name='course_sku_id' type='checkbox' value='" + sku.getId() + "' checked></td>");
                    }else{
                        sb.append("<td><input id='course_sku_id' name='course_sku_id' type='checkbox' value='" + sku.getId() + "'></td>");
                    }
                    sb.append("<td>");

                    sb.append("时间:"+sku.getStartTime().substring(0,16));
                    sb.append("<br>");
                    sb.append("<br>");
                    sb.append("地址:"+sku.getPlaceName());
                    sb.append("<br>");
                    sb.append("<br>");
                    sb.append("成人:"+sku.getAdult()+"&nbsp;&nbsp;儿童:"+sku.getChild());
                    sb.append("</td>");

                    sb.append("<td>");
//                    sb.append("总库存:"+sku.getStock()+";");
//                    sb.append("<br>");
                    sb.append("还有"+sku.getUnlockedStock()+"名额可以开放");
                    sb.append("<br>");
                    sb.append("<br>");
                    if (courseSkus.size() > 0) {
                        for (int i = 0; i < courseSkus.size(); i++) {
                            CourseSku courseSku = courseSkus.get(i);
                            sb.append("总试听名额&nbsp;&nbsp;").append("<input id='" + input_id + "' name='" + input_id + "' type='text' value='" + courseSku.getStock() + "' >");
                            sb.append("("+courseSku.getLockedStock()+"个已选)");
                            sb.append("<input id='" + input_hidden_id + "' name='" + input_hidden_id + "' type='hidden' value='" + courseSku.getLockedStock() + "' >");
                            sb.append("<input id='" + y_input_id + "' name='" + y_input_id + "' type='hidden' value='" + courseSku.getStock() + "' >");
                        }
                    }else{
                        sb.append("总试听名额&nbsp;&nbsp;").append("<input id='" + input_id + "' name='" + input_id + "' type='text' >");
                        sb.append("<input id='" + input_hidden_id + "' name='" + input_hidden_id + "' type='hidden' value='' >");
                        sb.append("<input id='" + y_input_id + "' name='" + y_input_id + "' type='hidden' value='' >");
                    }
                    sb.append("</td>");
                    sb.append("</tr>");
                }
            }
            sb.append(table_end);

        }

        return sb.toString();
    }

    private String getCopySkuHtmlNull(int course_id) {
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
                    sb.append("<td><input id='course_sku_id' name='course_sku_id' type='checkbox' value='"+sku.getId()+"'></td>");
                    sb.append("<td>");

                    sb.append("时间:"+sku.getStartTime().substring(0,16));
                    sb.append("<br>");
                    sb.append("<br>");
                    sb.append("地址:"+sku.getPlaceName());
                    sb.append("<br>");
                    sb.append("<br>");
                    sb.append("成人:"+sku.getAdult()+"&nbsp;&nbsp;儿童:"+sku.getChild());
                    sb.append("</td>");

                    sb.append("<td>");
//                    sb.append("总库存:"+sku.getStock()+";");
//                    sb.append("<br>");
                    sb.append("还有"+sku.getUnlockedStock()+"名额可以开放");
                    sb.append("<br>");
                    sb.append("<br>");
                    sb.append("总试听名额&nbsp;&nbsp;").append("<input id='" + input_id + "' name='" + input_id + "' >");
                    sb.append("</td>");
                    sb.append("</tr>");
                }
            }
            sb.append(table_end);

        }

        return sb.toString();
    }


    /**
     * 取消课程sku信息
     * @param course_id
     * @param id
     * @return
     */
    @Override
    public int update_CourseSku(int course_id,int id){
        int reSuccess = 0;
        List<Course> courses = courseService.getEntitysById(course_id);//获取子级试听课的集合
        if (courses.size() > 0){
            Course c_course  = courses.get(0);//获取试听课信息
            List<CourseSku> courseSkus = this.getCourseSkuById(c_course.getId(),id);//根据课程和sku的id获取对应的子级sku集合
            if (courseSkus.size() > 0){
                for (CourseSku c_courseSku : courseSkus){
                    this.updateBookedCourseAndPackage(c_course.getId(), c_courseSku.getId());
                    this.updateStatus(c_courseSku.getId(),Quantity.STATUS_THREE);
                }
            }
        }

        this.updateBookedCourseAndPackage(course_id, id);
        int int_update = this.updateStatus(id,Quantity.STATUS_THREE);
        if (int_update > 0){
            reSuccess = int_update;
        }

        return reSuccess;
    }


    /**
     * 更新课程包的剩余课程值
     * @param course_id
     * @param course_sku_id
     */
    public void updateBookedCourseAndPackage(int course_id, int course_sku_id){
        Set set = orderPackageService.getOrderPackage(course_id,course_sku_id);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            OrderPackage orderPackage = orderPackageService.get(Integer.parseInt(it.next().toString()));
            orderPackage.setBookableCount(orderPackage.getBookableCount()+1);
            orderPackageService.update(orderPackage);
        }
    }

    @Override
    public void update_un_check(int course_id, String[] course_sku_id){
        Course cour = courseService.get(course_id);
        Course c_cour = courseService.getEntitysById(cour.getId()).get(0);//试听课课程对象
        int c_sub_id = c_cour.getSubjectId();
        int c_cour_id = c_cour.getId();
        Subject subject = subjectService.get(c_sub_id);//试听课的课程体系对象
        List<CourseSku> courseSkus = this.getUnCkeckCourseSku(course_id,course_sku_id);
        if (courseSkus.size() > 0) {
            for (CourseSku courseSku : courseSkus) {
                int old_stock = courseSku.getStock();//父级sku对象的库存
                int old_un_stock = courseSku.getUnlockedStock();//父级sku对象的剩余库存
                List<CourseSku> c_courseSkus = this.getCourseSkuById(c_cour_id, courseSku.getId());//通过父级sku的id获取下属sku对象集合
                if (courseSkus.size() > 0) {
                    for (CourseSku c_courseSku : c_courseSkus){
                        int c_old_stock = c_courseSku.getStock();//如果有下属sku对象，获取第一个sku对象的库存
                        int c_old_un_stock = c_courseSku.getUnlockedStock();//如果有下属sku对象，获取第一个sku对象的剩余库存
                        if (c_old_un_stock == 0){
                            break;
                        }else{
                            c_old_stock = c_old_stock - c_old_un_stock;
                            c_courseSku.setStock(c_old_stock);
                            c_courseSku.setUnlockedStock(0);
                            this.update(c_courseSku);//更新所属sku

                            courseSku.setStock(old_stock+c_old_un_stock);
                            courseSku.setUnlockedStock(old_un_stock + c_old_un_stock);

                            this.update(courseSku);//更新所属sku

                            int subject_stock = subject.getStock() - c_old_un_stock;
                            subjectService.updateStock(subject.getId(),subject_stock);//更新试听课的课程体系stock

                        }
                    }
                }

            }
        }
    }

    private List<CourseSku> getUnCkeckCourseSku(int course_id, String[] course_sku_id){
        List<CourseSku> reData = new ArrayList<CourseSku>();
        String where = "";
        String sql = "SELECT * FROM SG_CourseSku where CourseId = "+course_id+" and ParentId = 0 and status > 0 and status != 3 ";
        if (course_sku_id != null){
            for (int i = 0; i < course_sku_id.length; i++) {

                where = where + " and id != " + course_sku_id[i];
            }
        }

        sql = sql + where;

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
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

                reData.add(entity);
            }
        }

        return reData;

    }

}
