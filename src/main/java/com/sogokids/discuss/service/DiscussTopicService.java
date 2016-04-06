package com.sogokids.discuss.service;

import com.sogokids.discuss.model.DiscussTopic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/2/23.
 */
public interface DiscussTopicService {
    public List<DiscussTopic> getEntitys();
    public DiscussTopic get(int id);
    public int insert(DiscussTopic entity);
    public int update(DiscussTopic entity);
    public int updateStatus(int id,int status);
    public int delete(int id);
    public DiscussTopic formEntity(HttpServletRequest request, int id);

    public String getPreview(int id);

}
