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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
