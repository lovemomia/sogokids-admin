package com.sogokids.subject.service;

import com.sogokids.subject.model.SubjectSku;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface SubjectSkuService {
    public List<SubjectSku> getEntitys();
    public List<SubjectSku> getModelsBySub_Key(int sub_id);
    public SubjectSku get(int id);
    public SubjectSku getSubjectSku(int sub_id, int cour_id);
    public int insert(SubjectSku entity);
    public int insertOne(SubjectSku entity);
    public int update(SubjectSku entity);
    public int updateStatus(int id, int status);
    public int delete(int id);
    public SubjectSku formEntity(HttpServletRequest request, int sub_id, int id);
    public SubjectSku formEntityOne(HttpServletRequest request, int sub_id, int id);

    public String getSkuHtml(int sub_id);
    public String getCourseSkuHtml(int course_id);
}
