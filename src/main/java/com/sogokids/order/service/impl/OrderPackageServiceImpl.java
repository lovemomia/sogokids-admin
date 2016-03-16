package com.sogokids.order.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.order.model.OrderPackage;
import com.sogokids.order.service.OrderPackageService;
import com.sogokids.subject.model.SubjectSku;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.system.model.Place;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoze on 15/11/10.
 */
@Service
public class OrderPackageServiceImpl implements OrderPackageService {

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private PlaceService placeService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<OrderPackage> getOrderPackageByOid(int order_id) {
        List<OrderPackage> reData = new ArrayList<OrderPackage>();
        String sql = "select Id,OrderId,SkuId,Price,CourseCount,BookableCount,Status,AddTime from SG_SubjectOrderPackage where Status > ? and OrderId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, order_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                OrderPackage entity = new OrderPackage();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setOrderId(Integer.parseInt(list.get(i).get("OrderId").toString()));
                int sub_sku_id = Integer.parseInt(list.get(i).get("SkuId").toString());
                entity.setSkuId(sub_sku_id);
                entity.setPrice(new BigDecimal(list.get(i).get("Price").toString()));
                entity.setCourseCount(Integer.parseInt(list.get(i).get("CourseCount").toString()));
                entity.setBookableCount(Integer.parseInt(list.get(i).get("BookableCount").toString()));

                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                SubjectSku sub_sku = subjectSkuService.get(sub_sku_id);
                sub_sku.setDateTime(sub_sku.getTime() + getDateStr(sub_sku.getTimeUnit()));
                entity.setSubjectSku(sub_sku);

                entity.setCourses(getOrderCourses(order_id,entity.getId()));

                this.getOrderPackageOrigin(entity);

                reData.add(entity);
            }
        }

        return reData;
    }


    private List<Course> getOrderCourses(int order_id, int order_package_id){
        List<Course> reData = new ArrayList<Course>();
        String sql = "select Id,CourseId,CourseSkuId from SG_BookedCourse where OrderId = ? and PackageId = ? and Status > ? ";
        Object [] params = new Object[]{order_id, order_package_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                int courseId = Integer.parseInt(list.get(i).get("CourseId").toString());
                int courseSkuId = Integer.parseInt(list.get(i).get("CourseSkuId").toString());
                Course course = courseService.get(courseId);
                List<CourseSku> courseSkus = new ArrayList<CourseSku>();
                CourseSku CourseSku = courseSkuService.get(courseSkuId);
                Place place = placeService.get(CourseSku.getPlaceId());
                CourseSku.setPlaceName(place.getName() + " " + place.getAddress());
                CourseSku.setStartTime(DateUtil.DateToStr_Cn(CourseSku.getStartTime()));
                courseSkus.add(CourseSku);
                course.setCourseSkus(courseSkus);
                reData.add(course);
            }

        }
        return reData;
    }

    private String getDateStr(int timeUnit){
        String reStr = "";
        switch (timeUnit) {
            case Quantity.STATUS_ONE:
                reStr = "月";
                break;
            case Quantity.STATUS_TWO:
                reStr = "季度";
                break;
            case Quantity.STATUS_THREE:
                reStr = "年";
                break;
        }

        return reStr;
    }

    @Override
    public Set getOrderPackage(int course_id, int course_sku_id){
        Set set = new HashSet();
        String sql = "select Id,PackageId from SG_BookedCourse where CourseId = ? and CourseSkuId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, course_sku_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        for (int i = 0; i < list.size(); i++) {
//            OrderPackage orderPackage = this.get(Integer.parseInt(list.get(i).get("PackageId").toString()));
            set.add(Integer.parseInt(list.get(i).get("PackageId").toString()));
            updateBookedCourse(Integer.parseInt(list.get(i).get("Id").toString()),Quantity.STATUS_ZERO);
        }

        return set;
    }

    private int updateBookedCourse(int id,int status){
        String sql = "update SG_BookedCourse set Status = ? where Id = ? ";
        Object [] params = new Object[]{status, id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public OrderPackage get(int id) {
        String sql = "select Id,OrderId,SkuId,Price,CourseCount,BookableCount,Status,AddTime from SG_SubjectOrderPackage where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final OrderPackage entity = new OrderPackage();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setOrderId(rs.getInt("OrderId"));
                entity.setSkuId(rs.getInt("SkuId"));
                entity.setPrice(rs.getBigDecimal("Price"));
                entity.setCourseCount(rs.getInt("CourseCount"));
                entity.setBookableCount(rs.getInt("BookableCount"));

                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

            }
        });

        return entity;
    }

    @Override
    public int update(OrderPackage entity) {
        String sql = "update SG_SubjectOrderPackage set BookableCount = ? where Id = ? ";
        Object [] params = new Object[]{entity.getBookableCount(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    /**
     * 计算购买来源
     * @param orderPackage
     * @return
     */
    private OrderPackage getOrderPackageOrigin(OrderPackage orderPackage){
        String sql = "SELECT a.Id,b.id as s_skuid,b.SubjectId,b.CourseId,b.Price,c.Type FROM SG_SubjectOrderPackage a, SG_SubjectSku b, SG_Subject c where a.Id = ? and a.SkuId = b.Id and b.SubjectId = c.Id and a.Status > ? and b.Status > ? and c.Status > ? and b.Status != ? ";
        Object [] params = new Object[]{orderPackage.getId(), Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                int type = Integer.parseInt(list.get(i).get("Type").toString());
                int courseId = Integer.parseInt(list.get(i).get("CourseId").toString());
//                String title = list.get(i).get("Title").toString();
                if (type == 2){
                    orderPackage.setOriginType(2);//试听课
                }else{
                    if (courseId == 0){
                        orderPackage.setOriginType(1);//课程包课程
                    }else{
                        orderPackage.setOriginType(3);//推荐
                    }
                }

            }
        }

        return orderPackage;

    }

}
