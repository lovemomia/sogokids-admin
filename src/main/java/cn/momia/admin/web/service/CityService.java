package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.City;
import cn.momia.admin.web.entity.Region;

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
    public City formEntity(HttpServletRequest request,int id);
    public List<City> getQueryPages(int start_row,int end_row);
}
