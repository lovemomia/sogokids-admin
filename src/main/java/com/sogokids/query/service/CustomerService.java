package com.sogokids.query.service;

import com.sogokids.query.model.Customer;

import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
public interface CustomerService {

    public Customer getCustomer(int id);
    public Customer getCustomerByMobile(String mobile);

    public int updateCustomer(int id);
    public int updateCustomerStatus(int id);

    public String getUserInfo(List<Long> uids);
}
