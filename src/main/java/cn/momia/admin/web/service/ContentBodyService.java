package cn.momia.admin.web.service;


import cn.momia.admin.web.entity.ContentBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/7/23.
 */
public interface ContentBodyService {
    public ContentBody get(int id);
    public List<ContentBody> getEntitys();
    public List<ContentBody> getEntitysByContentId(int contentId,String sort);
    public int insert(ContentBody entity);
    public int update(ContentBody entity);
    public int delete(int id);
    public ContentBody formEntity(HttpServletRequest request,int id);
    public ContentBody formEntity2(HttpServletRequest request);
}
