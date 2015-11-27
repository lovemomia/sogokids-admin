package com.sogokids.subject.service;

import com.sogokids.images.model.Images;
import com.sogokids.subject.model.SubjectImg;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface SubjectImgService {

    public List<SubjectImg> getEntitys();
    public List<SubjectImg> getModelsBySub_Key(int subjectId);
    public SubjectImg get(int id);
    public int insert(SubjectImg entity);
    public int update(SubjectImg entity);
    public int delete(int id);
    public SubjectImg formEntity(HttpServletRequest request, int id);
    public SubjectImg formEntity(Images img, int id);

    public  String getImgHtml(int sub_id);
}
