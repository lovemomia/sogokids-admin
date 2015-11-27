package com.sogokids.system.service;



import com.sogokids.system.model.Region;

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
    public List<Region> getEntitysByparentId(int parentId);
    public List<Region> getEntities(List<Region> regions);
    public int update(Region entity);
    public int delete(int id);
    public Region formEntity(HttpServletRequest request, int id);
    public List<Region> getEntitysBycpId(int cityId, int parentId);
}
