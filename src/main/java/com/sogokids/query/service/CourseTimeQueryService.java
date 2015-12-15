package com.sogokids.query.service;

import com.sogokids.query.model.CourseTimeQuery;
import com.sogokids.query.model.CourseUser;
import com.sogokids.query.model.MobileUser;

import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/12/10.
 */
public interface CourseTimeQueryService {

    /**
     * 时间查询课程信息
     * @param course_title
     * @param startDate
     * @param endDate
     * @return
     */
    public List<CourseTimeQuery> getCourseTimeQuerys(String course_title, String startDate, String endDate);

    /**
     * 时间查询课程信息－用户信息
     * @param courseId
     * @param skuId
     * @return
     */
    public List<CourseUser> getCourseUsers(int courseId, int skuId);

    /**
     * 电话查询用户信息
     * @param mobile
     * @return
     */
    public Map<String,Object> getMobileUsers(String mobile);

}
