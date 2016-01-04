package com.sogokids.system.service.impl;

import com.sogokids.system.model.Menu;
import com.sogokids.system.service.MenuService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoze on 15/12/30.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Override
    public String getMenuHtml(HttpServletRequest req, int uid){
        StringBuffer sb = new StringBuffer();
        List<Menu> menus = this.getMenus();
        String requestUri = req.getRequestURI();
        if (menus.size() > 0) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                if (menu.getUrl().equals(requestUri)){
                    if (menu.getId() == 1 || menu.getId() == 25) {
                        menu.setActive(1);
                    }
                }
            }
        }

        return sb.toString();
    }

    public List<Menu> getMenus(){
        //new Menu(id,url,name,,0)
        List<Menu> menus = new ArrayList<Menu>();
        menus.add(new Menu(1,"/user/index.do","主页",0,0,1));

        menus.add(new Menu(2,"/sub/info.do", "课程体系", 0,0,1));
        menus.add(new Menu(3,"/book/info.do", "试听课程", 0,0,1));
        menus.add(new Menu(4,"/one/info.do", "推荐课程", 0,0,1));
        menus.add(new Menu(5,"/group/info.do", "批量选课", 0,0,1));

        menus.add(new Menu(6,"0","查询统计",0,0,1));
        menus.add(new Menu(7,"/query/info.do","选课查询",0,6,1));
        menus.add(new Menu(8,"/query/order.do", "订单查询", 0,6,1));

        menus.add(new Menu(9,"0","首页配置",0,0,1));
        menus.add(new Menu(10,"/banner/info.do","banner设置",0,9,1));
        menus.add(new Menu(11,"/event/info.do", "event设置", 0,9,1));
        menus.add(new Menu(12,"/icon/info.do", "icon设置", 0,9,1));


        menus.add(new Menu(13,"0","系统设置",0,0,1));
        menus.add(new Menu(14,"/city/info.do","城市信息",0,13,1));
        menus.add(new Menu(15,"/region/info.do", "区域信息", 0,13,1));
        menus.add(new Menu(16,"/place/info.do", "商户信息", 0,13,1));
        menus.add(new Menu(17,"/inst/info.do", "机构信息", 0,13,1));
        menus.add(new Menu(18,"/teacher/info.do", "师资力量", 0,13,1));
        menus.add(new Menu(19,"/app/info.do", "APP版本", 0,13,1));

        menus.add(new Menu(20,"/coupon/info.do","优惠设置",0,0,1));

        menus.add(new Menu(21,"0","用户信息",0,0,1));
        menus.add(new Menu(22,"/user/info.do","用户信息",0,21,1));
        menus.add(new Menu(23,"/role/info.do", "角色设置", 0,21,1));
        menus.add(new Menu(24,"/func/info.do", "权限设置", 0,21,1));

        menus.add(new Menu(25,"/user/loginpage.do", "登录", 0,1,2));

        return menus;

    }

}
