package com.sogokids.teacher.service;

import com.sogokids.teacher.model.TeacherEducation;

import java.util.List;

/**
 * Created by hoze on 16/1/8.
 */
public interface TeacherEducationService {

    public TeacherEducation getTeacherEducation(int userId);
    public List<TeacherEducation> getTeacherEducations(int userId);
}
