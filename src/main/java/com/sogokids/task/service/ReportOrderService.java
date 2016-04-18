package com.sogokids.task.service;

import com.sogokids.task.model.ReportOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/12.
 */
public interface ReportOrderService {

    public int insert(ReportOrder entity);
    public List<ReportOrder> getEntitys();
    public Map getReportOrders();
    public void addReportOrder();
}
