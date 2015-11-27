package com.sogokids.order.service;

import com.sogokids.order.model.OrderPackage;

import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
public interface OrderPackageService {
    public List<OrderPackage> getOrderPackageByOid(int order_id);
}
