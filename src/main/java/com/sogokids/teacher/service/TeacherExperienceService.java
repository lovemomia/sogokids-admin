package com.sogokids.teacher.service;

import com.sogokids.teacher.model.TeacherExperience;

import java.util.List;

/**
 * Created by hoze on 16/1/8.
 */
public interface TeacherExperienceService {

    public List<TeacherExperience> getTeacherExperiences(int userId);

}
