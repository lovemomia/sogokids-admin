package com.sogokids.subject.service;

import com.sogokids.subject.model.SubjectNotice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface SubjectNoticeService {

    public List<SubjectNotice> getEntitys(int subId);
    public SubjectNotice get(int id);
    public int insert(SubjectNotice entity);
    public int update(SubjectNotice entity);
    public int delete(int id);
    public SubjectNotice formEntity(HttpServletRequest request, int subId, int id);

    public String getContents(int sub_id);
    public String getContentsHtml(int sub_id);
}
