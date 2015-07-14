package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.Tags;

import java.util.List;

/**
 * Created by hoze on 15/7/14.
 */
public interface TagsService {
    public List<Tags> getEntitys();
    public List<Tags> getTags(String tags);
}
