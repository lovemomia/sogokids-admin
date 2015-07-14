package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.Coupon;
import cn.momia.admin.web.entity.Place;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/7/14.
 */
public interface CouponService {

    public Coupon get(int id);
    public List<Coupon> getEntitys();
    public int insert(Coupon entity);
    public int update(Coupon entity);
    public int delete(int id);
    public Coupon formEntity(HttpServletRequest request,int id);
    public List<Coupon> getQueryPages(int start_row,int end_row);
}
