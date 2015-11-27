package com.sogokids.course.service;

import com.sogokids.course.model.CourseImg;
import com.sogokids.images.model.Images;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/16.
 */
public interface CourseImgService {

    public List<CourseImg> getEntitys();
    public List<CourseImg> getModelsBySub_Key(int courseId);
    public CourseImg get(int id);
    public int insert(CourseImg entity);
    public int update(CourseImg entity);
    public int delete(int id);
    public CourseImg formEntity(HttpServletRequest request, int id);
    public CourseImg formEntity(Images img, int id);

    public  String getImgHtml(int cou_id);
}
