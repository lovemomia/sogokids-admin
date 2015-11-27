package com.sogokids.query.service;

import com.sogokids.course.model.Course;
import com.sogokids.order.model.Order;
import com.sogokids.query.model.Customer;

import java.util.List;

/**
 * Created by hoze on 15/11/6.
 */
public interface QueryService {
    public List<Order> getOrders(String startTime, String endTime, String mobile, int status);
    public List<Course> getCourses(String startDate, String endDate, String course_title);
    public Course getCourseSkuByCourseId(int course_id);

}
