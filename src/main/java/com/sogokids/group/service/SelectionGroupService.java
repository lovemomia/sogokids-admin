package com.sogokids.group.service;

import com.sogokids.group.model.SelectionGroup;
import com.sogokids.query.model.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/12/25.
 */
public interface SelectionGroupService {

    public List<SelectionGroup> getEntitys();
    public SelectionGroup get(int id);
    public int insert(SelectionGroup entity);
    public int insertKey(SelectionGroup course);
    public int update(SelectionGroup entity);
    public int delete(int id);
    public SelectionGroup formEntity(HttpServletRequest request, int id);

    public String addGroup(HttpServletRequest req);
    public String addGroupUser(int gid, String mobiles);

    public List<Customer> getGroupCustomers(int gId,String where);
}
