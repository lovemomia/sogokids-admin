package com.sogokids.group.service;

import com.sogokids.group.model.GroupCourse;
import com.sogokids.http.model.HttpResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/12/29.
 */
public interface GroupCourseService {

    public List<GroupCourse> getEntitys();
    public GroupCourse get(int id);
    public GroupCourse isget(int gid);
    public int insert(GroupCourse entity);
    public int update(GroupCourse entity);
    public int delete(int id);
    public GroupCourse formEntity(HttpServletRequest request, int id);

    public HttpResult insertGroupCourse(int gId, int skuId);
}
