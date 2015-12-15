package com.sogokids.query.service;

import com.sogokids.query.model.Customer;

/**
 * Created by hoze on 15/11/10.
 */
public interface CustomerService {

    public Customer getCustomer(int id);
    public Customer getCustomerByMobile(String mobile);

    public int updateCustomer(int id);
}
