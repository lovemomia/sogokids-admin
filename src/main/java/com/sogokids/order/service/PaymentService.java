package com.sogokids.order.service;

import com.sogokids.order.model.Payment;

/**
 * Created by hoze on 15/11/10.
 */
public interface PaymentService {

    public Payment getPayment(int order_id);

}
