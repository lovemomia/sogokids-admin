package com.sogokids.course.service;

import com.sogokids.course.model.CourseSku;
import com.sogokids.subject.model.SubjectSku;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface CourseSkuService {
    public List<CourseSku> getEntitys();
    public List<CourseSku> getModelsByCourse_Key(int course_id);
    public List<CourseSku> getCourseSkuById(int id);
    public CourseSku get(int id);
    public int insert(CourseSku entity);
    public int update(CourseSku entity);
    public int delete(int id);
    public CourseSku formEntity(HttpServletRequest request, int course_id, int id);

    public String getSkuHtml(int course_id);
    public String getCopySkuHtml(int course_id);
    public String getSkuPlaceHtml(int cs_id);
}
