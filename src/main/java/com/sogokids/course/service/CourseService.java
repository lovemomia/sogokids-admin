package com.sogokids.course.service;

import com.sogokids.course.model.Course;
import com.sogokids.utils.entity.QzResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
public interface CourseService {
    public List<Course> getEntitys();
    public List<Course> getEntitysById(int id);
    public List<Course> getEntitysBySubId(int id);
    public List<Course> getCoursesBySubId(int id);
    public Course get(int id);
    public Course getRecommend(int id);
    public int insert(Course entity);
    public int insertKey(Course course);
    public int update(Course entity);
    public int updateStatus(int course_id, int status);
    public int updateNotice(int course_id, String notice);
    public int delete(int id);
    public Course formEntity(HttpServletRequest request, int id);
    public Course formEntityTrial(HttpServletRequest request, Course entity);

    //推荐课程
    public Map<String,Object> insert_setOneCourse(HttpServletRequest req);

    //取消推荐课程
    public int update_cancelCourse(int course_id);

    //加入试听课
    public int insert_copyCourseTrial(HttpServletRequest req);

    //取消试听课
    public int deleteTrialCourse(int course_id);

    //创建群组
    public QzResult createQz(HttpServletRequest req, int course_id);

    /**
     * 预览
     * @param course_id
     * @return
     */
    public String getPreview(int course_id);
}
