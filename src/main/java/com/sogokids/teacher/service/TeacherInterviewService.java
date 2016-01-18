package com.sogokids.teacher.service;

import com.sogokids.teacher.model.TeacherInterview;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hoze on 16/1/12.
 */
public interface TeacherInterviewService {
    public TeacherInterview getTeacherInterviews(int userId);

    public int insert(TeacherInterview entity);
    public TeacherInterview formEntity(HttpServletRequest request, int userId);
}
