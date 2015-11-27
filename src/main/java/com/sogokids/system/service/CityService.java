package com.sogokids.system.service;



import com.sogokids.system.model.City;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/6/25.
 */
public interface CityService {

    public List<City> getEntitys();
    public City get(int id);
    public int insert(City entity);
    public int update(City entity);
    public int delete(int id);
    public City formEntity(HttpServletRequest request, int id);
}
