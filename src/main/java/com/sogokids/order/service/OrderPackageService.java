package com.sogokids.order.service;

import com.sogokids.order.model.OrderPackage;

import java.util.List;
import java.util.Set;

/**
 * Created by hoze on 15/11/10.
 */
public interface OrderPackageService {
    public List<OrderPackage> getOrderPackageByOid(int order_id);

    public Set getOrderPackage(int course_id, int course_sku_id);

    public int update(OrderPackage entity);
    public OrderPackage get(int id);
}
