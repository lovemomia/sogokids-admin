package com.sogokids.system.service;



import com.sogokids.system.model.Place;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/6/15.
 */
public interface PlaceService {
    public Place get(int id);
    public List<Place> getEntitys();
    public int insert(Place entity);
    public int insertKey(Place entity);
    public int update(Place entity);
    public int delete(int id);
    public Place formEntity(HttpServletRequest request, int id);
    public String getDataJsonStr(int placeid);
}
