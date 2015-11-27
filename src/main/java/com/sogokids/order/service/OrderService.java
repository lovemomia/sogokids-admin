package com.sogokids.order.service;

import com.sogokids.order.model.Order;

import java.util.List;

/**
 * Created by hoze on 15/11/10.
 */
public interface OrderService {
    public List<Order> getOrders(String startTime, String endTime, String mobile, int status);

    public int updateOrder(int id);

    public Order getOrder(int id);
}
