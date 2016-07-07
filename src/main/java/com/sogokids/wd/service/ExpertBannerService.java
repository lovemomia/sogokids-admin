package com.sogokids.wd.service;

import com.sogokids.wd.model.ExpertBanner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 16/6/27.
 */
public interface ExpertBannerService {

    public List<ExpertBanner> getEntitys();
    public ExpertBanner get(int id);
    public int insert(ExpertBanner entity);
    public int update(ExpertBanner entity);
    public int delete(int id);
    public ExpertBanner formEntity(HttpServletRequest request, int id);
}
