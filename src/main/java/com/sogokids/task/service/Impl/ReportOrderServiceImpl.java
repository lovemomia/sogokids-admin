package com.sogokids.task.service.Impl;

import com.sogokids.order.model.OrderPackage;
import com.sogokids.order.service.OrderPackageService;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.subject.service.SubjectSkuService;
import com.sogokids.task.model.ReportOrder;
import com.sogokids.task.service.ReportOrderService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/4/12.
 */
@Service
public class ReportOrderServiceImpl implements ReportOrderService {

    private final Logger log = LoggerFactory.getLogger(ReportOrderServiceImpl.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private OrderPackageService orderPackageService;

    @Autowired
    private SubjectSkuService subjectSkuService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insert(ReportOrder entity) {
        String sql = "insert into SG_ReportOrder(OrderDate, OrderNumber,OrderType, Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getOrderDate(), entity.getOrderNumber(), entity.getOrderType(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            log.error("添加统计订单"+entity.getOrderType()+"->error:"+_ex.getMessage());
        }
        return reData;
    }

    @Override
    public List<ReportOrder> getEntitys() {

        List<ReportOrder> reData = new ArrayList<ReportOrder>();
        String d_date = DateUtil.NowDateW()+" 00:00:00";
        String q_date = DateUtil.getDate7Day()+" 00:00:00";

        String sql = "select id,OrderDate, OrderNumber,OrderType, Status,AddTime from SG_ReportOrder where status > ? and OrderDate < ? and OrderDate >= ?";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,d_date,q_date};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ReportOrder entity = new ReportOrder();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setOrderDate(list.get(i).get("OrderDate").toString());
                entity.setOrderNumber(Integer.parseInt(list.get(i).get("OrderNumber").toString()));
                entity.setOrderType(Integer.parseInt(list.get(i).get("OrderType").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public Map getReportOrders(){
        List<ReportOrder> reportOrders = this.getEntitys();
        StringBuffer x_date = new StringBuffer();
        StringBuffer y_st_number = new StringBuffer();
        StringBuffer y_tj_number = new StringBuffer();
        StringBuffer y_kc_number = new StringBuffer();
        x_date.append("[");
        y_st_number.append("[");
        y_tj_number.append("[");
        y_kc_number.append("[");
        for (ReportOrder reportOrder : reportOrders){

            int number = reportOrder.getOrderNumber();
            if (reportOrder.getOrderType() == 1){
                x_date.append("\""+DateUtil.getDateStr_en(reportOrder.getOrderDate())+"\"").append(",");
                y_st_number.append(number).append(",");
            }
            if (reportOrder.getOrderType() == 2){
                y_tj_number.append(number).append(",");
            }
            if (reportOrder.getOrderType() == 3){
                y_kc_number.append(number).append(",");
            }

        }
        x_date.append("]");
        y_st_number.append("]");
        y_tj_number.append("]");
        y_kc_number.append("]");

        Map map = new HashMap();
        map.put("x_order_date",x_date);
        map.put("y_st_number",y_st_number);
        map.put("y_tj_number",y_tj_number);
        map.put("y_kc_number",y_kc_number);

        return map;
    }

    @Override
    public void addReportOrder() {
        String before_day = DateUtil.getDatePreDay();
        String min_date = before_day+" 00:00:00";
        String max_date = before_day+" 23:59:59";
        int st_number = 0;
        int tj_number = 0;
        int kc_number = 0;

        String sql = "select * from SG_SubjectOrder where Status >= ? and addTime <= ? and addTime >= ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, Quantity.STATUS_THREE,max_date,min_date);
        if(list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                int order_id = Integer.parseInt(list.get(i).get("Id").toString());
                int type = subjectService.get(sub_id).getType();
                if (type == 2){
                    st_number = st_number + 1;//试听课程
                }else{
                    int subType = getOrderType(order_id);
                    if (subType > 0){
                        if (subType == 1){
                            tj_number = tj_number + 1;
                        }else if(subType == 2){
                            kc_number = kc_number + 1;
                        }
                    }
                }
            }
        }
        ReportOrder entity = new ReportOrder();
        entity.setOrderDate(before_day);
        entity.setOrderNumber(st_number);
        entity.setOrderType(Quantity.STATUS_ONE);//试听
        this.insert(entity);
        entity.setOrderNumber(tj_number);
        entity.setOrderType(Quantity.STATUS_TWO);//推荐
        this.insert(entity);
        entity.setOrderNumber(kc_number);
        entity.setOrderType(Quantity.STATUS_THREE);//课程
        this.insert(entity);
    }

    private int getOrderType(int order_id){
        int reDate = 0;
       List<OrderPackage> orderPackages = orderPackageService.getOrderPackageByOid(order_id);
        if (orderPackages.size() > 0){
            OrderPackage orderPackage = orderPackages.get(0);
            int sub_sku_id = orderPackage.getSkuId();
            int course_id = subjectSkuService.get(sub_sku_id).getCourseId();
            if (course_id > 0){
                reDate = 1;//推荐课程
            }else{
                reDate = 2;//课程包
            }
        }
        return reDate;
    }
}
