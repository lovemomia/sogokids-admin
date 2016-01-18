package com.sogokids.teacher.service;

import com.sogokids.teacher.model.CourseMaterial;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/1/8.
 */
public interface CourseMaterialService {
    public List<CourseMaterial> getMaterials();

    public CourseMaterial get(int id);
    public int insert(CourseMaterial entity);
    public int update(CourseMaterial entity);
    public int delete(int id);
    public CourseMaterial formEntity(HttpServletRequest request, int id);

    public boolean isMaterial(int courseId);
}
