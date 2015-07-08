package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.Region;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/7/6.
 */
public interface RegionService {

    public Region get(int id);
    public List<Region> getEntitys();
    public int insert(Region entity);
    public List<Region> getEntitysByKey(int cityId);
    public List<Region> getEntities(List<Region> regions);
    public int update(Region entity);
    public int delete(int id);
    public Region formEntity(HttpServletRequest request,int id);
    public List<Region> getQueryPages(int start_row,int end_row);

}
