package com.sogokids.order.service.impl;

import cn.momia.common.config.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.http.model.HttpResult;
import com.sogokids.http.service.HttpClientService;
import com.sogokids.order.model.Order;
import com.sogokids.order.model.OrderPackage;
import com.sogokids.order.model.Payment;
import com.sogokids.order.service.OrderPackageService;
import com.sogokids.order.service.OrderService;
import com.sogokids.order.service.PaymentService;
import com.sogokids.query.service.CustomerService;
import com.sogokids.subject.service.SubjectService;
import com.sogokids.utils.util.CastUtil;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import com.sogokids.utils.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/11/10.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderPackageService orderPackageService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private HttpClientService httpClientService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Order> getOrders(String startTime, String endTime, String mobile, int status) {
        List<Order> reData = new ArrayList<Order>();
        String where = "where Status > 0 ";

        if (startTime != null && !startTime.equals("")){
            startTime = startTime + " 00:00:00";
            where = where + " and AddTime > " + "'" + startTime + "'";
        }
        if (endTime != null && !endTime.equals("")){
            endTime = endTime + " 23:59:59";
            where = where + " and AddTime < " + "'" + endTime + "'";
        }
        if (!mobile.equals("") && mobile != null){
            where = where + " and Mobile LIKE '%" + mobile +"%'";
        }
        if (status > 0){
            int q_status = status + 1;
            where = where + " and "+"(Status = " + status + " or Status = " + q_status+") ";

        }
        String sql = "select Id,UserId,SubjectId,Contact,Mobile,inviteCode,Status,AddTime from SG_SubjectOrder " + where + " order by AddTime desc";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Order entity = new Order();
                int id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(id);
                int user_id = Integer.parseInt(list.get(i).get("UserId").toString());
                int sub_id = Integer.parseInt(list.get(i).get("SubjectId").toString());
                entity.setUserId(user_id);
                entity.setSubjectId(sub_id);
                entity.setContact(list.get(i).get("Contact").toString());
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setInviteCode(list.get(i).get("inviteCode").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                entity.setCustomer(customerService.getCustomer(user_id));
                entity.setSubject(subjectService.get(sub_id));
                Payment payment = paymentService.getPayment(id);
                if (payment.getFinishTime() != null && !payment.getFinishTime().equals("")) {
                    payment.setFinishTime(DateUtil.DateToStr_Cn(payment.getFinishTime()));
                }
                entity.setPayment(payment);
                entity.setOrderPackages(orderPackageService.getOrderPackageByOid(id));
                if (entity.getOrderPackages().size() > 0) {
                    if (entity.getOrderPackages().size() == 1) {
                        entity.setPriceSum(entity.getOrderPackages().get(0).getPrice());
                    } else {
                        entity.setPriceSum(getZPrices(entity));
                    }
                }else{
                    entity.setPriceSum(new BigDecimal(0));
                }
                entity.setTk_mark(getTK(entity));
                reData.add(entity);
            }
        }

        return reData;
    }



    @Override
    public int updateOrder(int id) {
        String sql = "update SG_SubjectOrder set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_FIVE,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public Order getOrder(int id) {
        String sql = "select Id,UserId,SubjectId,Contact,Mobile,inviteCode,Status,AddTime from SG_SubjectOrder where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final Order entity = new Order();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                int id = rs.getInt("Id");
                int user_id = rs.getInt("UserId");
                int sub_id = rs.getInt("SubjectId");
                entity.setId(id);
                entity.setUserId(user_id);
                entity.setSubjectId(sub_id);
                entity.setContact(rs.getString("Contact"));
                entity.setMobile(rs.getString("Mobile"));
                entity.setInviteCode(rs.getString("inviteCode"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

                entity.setCustomer(customerService.getCustomer(user_id));
                entity.setSubject(subjectService.get(sub_id));
                entity.setPayment(paymentService.getPayment(id));
                entity.setOrderPackages(orderPackageService.getOrderPackageByOid(id));
                if (entity.getOrderPackages().size() > 0) {
                    if (entity.getOrderPackages().size() == 1) {
                        entity.setPriceSum(entity.getOrderPackages().get(0).getPrice());
                    } else {
                        entity.setPriceSum(getZPrices(entity));
                    }
                }else{
                    entity.setPriceSum(new BigDecimal(0));
                }

                entity.setTk_mark(getTK(entity));

            }
        });

        return entity;
    }

    private BigDecimal getZPrices(Order order){
        BigDecimal sum = null;
        List<OrderPackage> order_package_ls = order.getOrderPackages();
        for (int i = 0; i < order_package_ls.size(); i++) {
            if (i == 0){
                sum = order_package_ls.get(i).getPrice();
            }else{
                sum = sum.add(order_package_ls.get(i).getPrice());
            }
        }

        return sum;
    }

    private int getTK(Order order){
        int sum = 0;
        List<OrderPackage> order_package_ls = order.getOrderPackages();
        for (int i = 0; i < order_package_ls.size(); i++) {
            int courseCount = order_package_ls.get(i).getCourseCount();
            int bookableCount = order_package_ls.get(i).getBookableCount();
            if (courseCount > bookableCount){
                sum = 1;
                break;
            }
        }

        return sum;
    }

    @Override
    public List<Order> getRefundOrders(){
        List<Order> reData = new ArrayList<Order>();
        String sql = "select Id,UserId,SubjectId,Contact,Mobile,inviteCode,refundMessage,Status,AddTime from SG_SubjectOrder where Status = ? order by AddTime desc";
        Object [] params = new Object[]{Quantity.STATUS_FIVE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Order entity = new Order();
                int id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(id);
                int user_id = Integer.parseInt(list.get(i).get("UserId").toString());
                entity.setUserId(user_id);
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));
                entity.setContact(list.get(i).get("Contact").toString());
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setInviteCode(list.get(i).get("inviteCode").toString());
                entity.setRefundMessage(list.get(i).get("refundMessage") == null ? "":list.get(i).get("refundMessage").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int refundOrder(int id){
        String upload_refund_order_url = configuration.getString(Quantity.UPLOAD_ORDER_REFUND);
        StringBuffer sb = new StringBuffer();
        long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
        sb.append("expired=").append(expired);
        sb.append("oid=").append(id);
        sb.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

        String sign = StringUtil.getSign(sb.toString());//请求加密串

        String param = "expired="+expired+"&oid="+id+"&sign="+sign;
        log.info("OrderServiceImpl -> RefundOrder -> info:确认退款组装参数结束.");
        log.info("OrderServiceImpl -> RefundOrder -> info:确认退款发出请求.");
        JSONObject jsonObject = httpClientService.httpPost(upload_refund_order_url, param);
        HttpResult result = CastUtil.toObject(jsonObject, HttpResult.class);
        log.info("OrderServiceImpl -> RefundOrder -> info:确认退款发出请求结束,返回errno:"+result.getErrno()+"=="+result.getErrmsg());

        return result.getErrno();
    }

}
