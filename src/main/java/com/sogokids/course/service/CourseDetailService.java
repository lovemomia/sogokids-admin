package com.sogokids.course.service;

import com.sogokids.course.model.CourseDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/26.
 */
public interface CourseDetailService {

    public List<CourseDetail> getEntitys();
    public List<CourseDetail> getModelsByCourse_Key(int course_id);
    public CourseDetail get(int id);
    public int insert(CourseDetail entity);
    public int insertKey(CourseDetail courseDetail);
    public int update(CourseDetail entity);
    public int delete(int id);
    public CourseDetail formEntity(HttpServletRequest request, int course_id, int id);
    public CourseDetail setCourseDetail(HttpServletRequest req,int course_id, int id);

    public String getDetailContentStr(int course_id);
}
