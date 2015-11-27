package com.sogokids.home.service;


import com.sogokids.home.model.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/12.
 */
public interface BannerService {

    public List<Banner> getEntitys();
    public Banner get(int id);
    public int insert(Banner entity);
    public int update(Banner entity);
    public int delete(int id);
    public Banner formEntity(HttpServletRequest request, int id);

}
