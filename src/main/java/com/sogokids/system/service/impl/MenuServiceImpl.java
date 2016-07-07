package com.sogokids.system.service.impl;

import com.sogokids.system.model.Menu;
import com.sogokids.system.service.MenuService;
import com.sogokids.user.service.FuncService;
import com.sogokids.utils.util.MenuUtil;
import com.sogokids.utils.util.Quantity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 * Created by hoze on 15/12/30.
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private FuncService funcService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Menu> getMenus() {
        List<Menu> reData = new ArrayList<Menu>();
        String sql = "select Id,MenuCode,MenuName,MenuUrl,MenuActive,Sequence,ParentId,Icon from SG_Menu where ParentId = ? order by Sequence asc";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Menu entity = new Menu();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setMenuCode(list.get(i).get("MenuCode").toString());
                entity.setMenuName(list.get(i).get("MenuName").toString());
                entity.setMenuUrl(list.get(i).get("MenuUrl").toString());
                entity.setMenuActive(Integer.parseInt(list.get(i).get("MenuActive").toString()));
                entity.setSequence(Integer.parseInt(list.get(i).get("Sequence").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setIcon(list.get(i).get("Icon").toString());
                entity.setMenus(getChildMenus(entity.getId()));

                reData.add(entity);
            }
        }

        return reData;
    }

    /**
     * 根据父级id获取子级菜单信息
     * @param id
     * @return
     */
    private List<Menu> getChildMenus(int id) {
        List<Menu> reData = new ArrayList<Menu>();
        String sql = "select Id,MenuCode,MenuName,MenuUrl,MenuActive,Sequence,ParentId,Icon from SG_Menu where ParentId = ? order by Sequence asc";
        Object [] params = new Object[]{id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Menu entity = new Menu();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setMenuCode(list.get(i).get("MenuCode").toString());
                entity.setMenuName(list.get(i).get("MenuName").toString());
                entity.setMenuUrl(list.get(i).get("MenuUrl").toString());
                entity.setMenuActive(Integer.parseInt(list.get(i).get("MenuActive").toString()));
                entity.setSequence(Integer.parseInt(list.get(i).get("Sequence").toString()));
                entity.setParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setIcon(list.get(i).get("Icon").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    /**
     * 根据权限获取菜单的html字符串
     * @param uid
     * @param menuCode
     * @param menuChildCode
     * @return
     */
    public String getMenuHtml(int uid,String menuCode,String menuChildCode){
        StringBuffer sb = new StringBuffer();
        List<Menu> menus = this.getMenus();
        String func_url_str = funcService.getDistinctFunc(uid);
        for (Menu menu : menus){
            String url = menu.getMenuUrl();
            String sy_url = menu.getMenuUrl()+"?uid="+uid;
            if (StringUtils.isEmpty(url)){
                StringBuffer sb_0 = new StringBuffer();
                String str = "<li><a href='index.jsp#'><i class='"+menu.getIcon()+"'></i> <span class='nav-label'>"+menu.getMenuName()+"</span><span class='fa arrow'></span></a><ul class='nav nav-second-level'>";
                List<Menu> menus_c = menu.getMenus();
                StringBuffer sb_1 = new StringBuffer();
                for (Menu menu_c : menus_c) {
                    String url_c = menu_c.getMenuUrl();
                    String sy_url_c = menu_c.getMenuUrl()+"?uid="+uid;
                    if (func_url_str.indexOf(url_c) > -1) {
                        if (menuChildCode.equals(menu_c.getMenuCode())) {
                            sb_1.append("<li class='active'><a href='" + sy_url_c + "'><i class='" + menu_c.getIcon() + "'></i> <span class='nav-label'>" + menu_c.getMenuName() + "</span> </a></li>");
                            str = str.replace("<li>","<li class='active'>");
                        } else {
                            sb_1.append("<li><a href='" + sy_url_c + "'><i class='" + menu_c.getIcon() + "'></i> <span class='nav-label'>" + menu_c.getMenuName() + "</span> </a></li>");
                        }
                    }
                }
                if (StringUtils.isNotEmpty(sb_1)) {
                    sb_0.append(str);
                    sb_0.append(sb_1);
                    sb_0.append("</ul></li>");
                    sb.append(sb_0);
                }
            }else{
                if (func_url_str.indexOf(url) > -1) {
                    if (menuCode.equals(menu.getMenuCode())) {
                        sb.append("<li class='active'><a href='" + sy_url + "'><i class='" + menu.getIcon() + "'></i> <span class='nav-label'>" + menu.getMenuName() + "</span> </a></li>");
                    } else {
                        sb.append("<li><a href='" + sy_url + "'><i class='" + menu.getIcon() + "'></i> <span class='nav-label'>" + menu.getMenuName() + "</span> </a></li>");
                    }
                }
            }
        }


        return sb.toString();
    }


    /**
     * 按照参数获取菜单信息
     * @param uid
     * @param int_x
     * @return
     */
    @Override
    public String getMenuStrings(int uid,int int_x) {
        String menus = "";
        switch (int_x) {
            case Quantity.STATUS_ONE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_INDEX_CODE, MenuUtil.MENU_CODE);
                break;
            case Quantity.STATUS_TWO:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SUB_CODE, MenuUtil.MENU_CODE);
                break;
            case Quantity.STATUS_THREE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_BOOKED_CODE, MenuUtil.MENU_CODE);
                break;
            case Quantity.STATUS_FOUR:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_ONE_CODE, MenuUtil.MENU_CODE);
                break;
            //查询统计
            case Quantity.STATUS_FIVE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_QUERY_CODE, MenuUtil.MENU_QUERY_INFO_CODE);
                break;
            case Quantity.STATUS_SIX:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_QUERY_CODE, MenuUtil.MENU_QUERY_ORDER_CODE);
                break;
            case Quantity.STATUS_SEVEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_QUERY_CODE, MenuUtil.MENU_REPORT_CODE);
                break;
            case Quantity.STATUS_THIRTY_ONE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_QUERY_CODE, MenuUtil.MENU_QUERY_REFUND_CODE);
                break;

            case Quantity.STATUS_EIGHT:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_DISCUSS_CODE, MenuUtil.MENU_CODE);
                break;

            //讲师管理
            case Quantity.STATUS_NINE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_TEACHER_CODE, MenuUtil.MENU_TEACHER_CHECK_CODE);
                break;
            case Quantity.STATUS_TEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_TEACHER_CODE, MenuUtil.MENU_TEACHER_INFO_CODE);
                break;
            case Quantity.STATUS_ELEVEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_TEACHER_CODE, MenuUtil.MENU_TEACHER_ASSIGN_CODE);
                break;
            case Quantity.STATUS_TWELVE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_TEACHER_CODE, MenuUtil.MENU_TEACHER_MATERIAL_CODE);
                break;

            case Quantity.STATUS_THIRTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_GROUP_CODE, MenuUtil.MENU_CODE);
                break;

            //首页配置
            case Quantity.STATUS_FOURTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_HOME_CODE, MenuUtil.MENU_HOME_BANNER_CODE);
                break;
            case Quantity.STATUS_FIFTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_HOME_CODE, MenuUtil.MENU_HOME_EVENT_CODE);
                break;
            case Quantity.STATUS_SIXTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_HOME_CODE, MenuUtil.MENU_HOME_ICON_CODE);
                break;
            case Quantity.STATUS_THIRTY_TWO:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_HOME_CODE, MenuUtil.MENU_HOME_RECOMMEND_CODE);
                break;

            //合作单位
            case Quantity.STATUS_SEVENTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_COOP_CODE, MenuUtil.MENU_COOP_INFO_CODE);
                break;
            case Quantity.STATUS_EIGHTEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_COOP_CODE, MenuUtil.MENU_COOP_USER_CODE);
                break;
            case Quantity.STATUS_NINETEEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_COOP_CODE, MenuUtil.MENU_COOP_ACTIVITY_CODE);
                break;

            //系统设置
            case Quantity.STATUS_TWENTY:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SYSTEM_CODE, MenuUtil.MENU_SYSTEM_CITY_CODE);
                break;
            case Quantity.STATUS_TWENTY_ONE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SYSTEM_CODE, MenuUtil.MENU_SYSTEM_REGION_CODE);
                break;
            case Quantity.STATUS_TWENTY_TWO:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SYSTEM_CODE, MenuUtil.MENU_SYSTEM_PLACE_CODE);
                break;
            case Quantity.STATUS_TWENTY_THREE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SYSTEM_CODE, MenuUtil.MENU_SYSTEM_INST_CODE);
                break;
            case Quantity.STATUS_TWENTY_FOUR:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_SYSTEM_CODE, MenuUtil.MENU_SYSTEM_APP_CODE);
                break;

            case Quantity.STATUS_TWENTY_FIVE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_COUPON_CODE, MenuUtil.MENU_CODE);
                break;

            //用户管理
            case Quantity.STATUS_TWENTY_SIX:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_USER_CODE, MenuUtil.MENU_USER_INFO_CODE);
                break;
            case Quantity.STATUS_TWENTY_SEVEN:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_USER_CODE, MenuUtil.MENU_USER_ROLE_CODE);
                break;
            case Quantity.STATUS_TWENTY_EIGHT:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_USER_CODE, MenuUtil.MENU_USER_FUNC_CODE);
                break;

            //拼团管理
            case Quantity.STATUS_TWENTY_NINE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_DEAL_CODE, MenuUtil.MENU_DEAL_SUBJECT_CODE);
                break;
            case Quantity.STATUS_THIRTY:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_DEAL_CODE, MenuUtil.MENU_DEAL_GROUP_CODE);
                break;

            //问答应用
            case Quantity.STATUS_THIRTY_THREE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_EXPERT_ZCODE, MenuUtil.MENU_EXPERT_BANNER_CODE);
                break;
            case Quantity.STATUS_THIRTY_FOUR:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_EXPERT_ZCODE, MenuUtil.MENU_EXPERT_CODE);
                break;
            case Quantity.STATUS_THIRTY_FIVE:
                menus = this.getMenuHtml(uid, MenuUtil.MENU_EXPERT_ZCODE, MenuUtil.MENU_EXPERT_COURSE_CODE);
                break;
        }

        return menus;
    }

}
