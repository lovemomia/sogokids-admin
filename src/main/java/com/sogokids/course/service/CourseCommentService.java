package com.sogokids.course.service;

import com.sogokids.course.model.CourseComment;

import java.util.List;

/**
 * Created by hoze on 16/2/19.
 */
public interface CourseCommentService {
    public List<CourseComment> getTCourseComments(int sub_id);
    public List<CourseComment> getNTCourseComments(int sub_id);

    public int update_Recommended(int id,int recommended);
}
