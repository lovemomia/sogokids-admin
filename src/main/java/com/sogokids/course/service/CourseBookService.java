package com.sogokids.course.service;

import com.sogokids.course.model.CourseBook;
import com.sogokids.images.model.Images;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface CourseBookService {
    public List<CourseBook> getEntitys();
    public List<CourseBook> getEntitys(int course_id);
    public CourseBook get(int id);
    public int insert(CourseBook entity);
    public int update(CourseBook entity);
    public int delete(int id);
    public CourseBook formEntity(Images images, int courseId, int id);

    public String getBooksHtml(int course_id);
}
