package com.sogokids.coupon.service;

import com.sogokids.coupon.model.Coupon;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/11/15.
 */
public interface CouponService {
    public Coupon get(int id);
    public List<Coupon> getEntitys();
    public int insert(Coupon entity);
    public int update(Coupon entity);
    public int delete(int id);
    public Coupon formEntity(HttpServletRequest request, int id);
}
