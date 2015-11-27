package com.sogokids.course.service;

import com.sogokids.course.model.Course;
import com.sogokids.course.model.CourseRecommend;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/11/21.
 */
public interface CourseRecommendService {

    public List<CourseRecommend> getEntitys();
    public List<Course> getCourses();
    public List<CourseRecommend> getEntitysByCourseId(int id);
    public List<CourseRecommend> getCancelByCourseId(int id);
    public CourseRecommend get(int id);
    public int insert(CourseRecommend entity);
    public int insertKey(CourseRecommend courseRecommend);
    public int updateStatus(int id);
    public int update(CourseRecommend entity);
    public int delete(int id);
    public CourseRecommend formEntity(HttpServletRequest request, int course_id, int id);
}
