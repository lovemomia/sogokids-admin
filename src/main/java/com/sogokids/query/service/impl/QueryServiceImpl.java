package com.sogokids.query.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseService;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.order.model.Order;
import com.sogokids.order.model.OrderPackage;
import com.sogokids.order.service.OrderService;
import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.query.service.QueryService;
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
 * Created by hoze on 15/11/6.
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

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
    public List<Order> getOrders(String startTime, String endTime, String mobile, int status){
        return orderService.getOrders(startTime, endTime, mobile, status);
    }

    @Override
    public List<Course> getCourses(String startDate, String endDate, String course_title) {
        List<Course> reData = new ArrayList<Course>();
        String where = "where Status > 0 and ParentId = 0 ";
        if (!startDate.equals("") && startDate != null){
            where = where + " and AddTime > " + "'" + startDate + "'";
        }
        if (!endDate.equals("") && endDate != null){
            where = where + " and AddTime < " + "'" + endDate + "'";
        }
        if (!course_title.equals("") && course_title != null){
            where = where + " and Title LIKE '%" + course_title +"%'";
        }
        String sql = "select Id,Title,AddTime from SG_Course " + where + " order by Id desc";

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Course entity = new Course();
                int id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(id);
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setAddTime(list.get(i).get("AddTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Course getCourseSkuByCourseId(int course_id) {
        Course course = courseService.get(course_id);

        List<CourseSku> course_sku_ls = courseSkuService.getModelsByCourse_Key(course.getId());
        if (course_sku_ls.size() > 0){
            for (int i = 0; i < course_sku_ls.size(); i++) {
                int sum = 0;
                CourseSku entity = course_sku_ls.get(i);
                List<Customer> customers = new ArrayList<Customer>();
                List<Customer> p_customers = this.getSkuCustomers(course.getId(),entity.getId());
                if (p_customers.size() > 0) {
                    customers.addAll(p_customers);
                    sum = sum + p_customers.size();
                }

                List<CourseSku> c_sku_ls = courseSkuService.getCourseSkuById(entity.getId());
                if (c_sku_ls.size() > 0){
                    for (int j = 0; j < c_sku_ls.size(); j++) {
                        CourseSku c_entity = c_sku_ls.get(j);
                        List<Customer> c_customers = this.getSkuCustomers(c_entity.getCourseId(),c_entity.getId());
                        if (c_customers.size() > 0) {
                            customers.addAll(c_customers);
                            sum = sum + c_customers.size();
                        }
                    }
                }
                entity.setSum(sum);
                entity.setCustomers(customers);
            }
        }

        course.setCourseSkus(course_sku_ls);

        return course;
    }

    private List<Customer> getSkuCustomers(int course_id, int course_sku_id){
        List<Customer> reData = new ArrayList<Customer>();
        String sql = "select Id,UserId from SG_BookedCourse where CourseId = ? and CourseSkuId = ? and Status > ? ";
        Object [] params = new Object[]{course_id, course_sku_id, Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                int userId = Integer.parseInt(list.get(i).get("UserId").toString());
                reData.add(customerService.getCustomer(userId));
            }

        }
        return reData;
    }

}
