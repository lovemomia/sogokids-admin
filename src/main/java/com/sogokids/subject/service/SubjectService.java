package com.sogokids.subject.service;

import com.sogokids.course.model.Course;
import com.sogokids.subject.model.Subject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/10/12.
 */
public interface SubjectService {
    public List<Subject> getEntitys();
    public List<Subject> getEntitys(int type);
    public List<Subject> getModelsByType();
    public List<Course> getCoursesBySubjects();
    public Subject get(int id);
    public int insert(Subject entity);
    public int insertKey(Subject entity);
    public int update(Subject entity);
    public int updateNotice(int sub_id, String contents);
    public int updateStatus(int sub_id, int status);
    public int updateStock(int sub_id, int stock);
    public int delete(int id);
    public Subject formEntity(HttpServletRequest request, int id);

    /*预览 */
    public String getPreview(int sub_id);

    public Map<String,String> getCommentHtml(int sub_id);
}
