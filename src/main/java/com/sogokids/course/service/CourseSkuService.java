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
    public List<CourseSku> getCourseSkuById(int course_id, int parentId);
    public CourseSku get(int id);
    public int insert(CourseSku entity);
    public int update(CourseSku entity);
    public int delete(int id);
    public CourseSku formEntity(HttpServletRequest request, int course_id, int id);

    public String getSkuHtml(int course_id);
    public String getCopySkuHtml(int course_id);
    public String getSkuPlaceHtml(int cs_id);

    public int update_CourseSku(int course_id,int id);

    //处理未勾选的试听课的sku信息，清除剩余库存，添加到正常课程的sku信息上
    public void update_un_check(int course_id, String[] course_sku_id);
}
