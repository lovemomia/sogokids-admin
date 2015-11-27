package com.sogokids.system.service;

import com.sogokids.system.model.Institution;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/10/20.
 */
public interface InstitutionService {

    public List<Institution> getEntitys();
    public Institution get(int id);
    public int insert(Institution entity);
    public int update(Institution entity);
    public int delete(int id);
    public Institution formEntity(HttpServletRequest request, int id);

}
