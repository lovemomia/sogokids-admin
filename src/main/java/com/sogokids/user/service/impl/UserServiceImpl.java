package com.sogokids.user.service.impl;


import com.sogokids.user.model.AdminRole;
import com.sogokids.user.model.Func;
import com.sogokids.user.model.RoleFunc;
import com.sogokids.user.model.User;
import com.sogokids.user.service.AdminRoleService;
import com.sogokids.user.service.FuncService;
import com.sogokids.user.service.RoleFuncService;
import com.sogokids.user.service.RoleService;
import com.sogokids.user.service.UserService;
import com.sogokids.utils.util.Menu;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/8/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleFuncService roleFuncService;

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
    public User get(int id) {
        String sql = "select id,username,password,status,addTime from SG_Admin where id = ? and status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final User entity = new User();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUsername(rs.getString("username"));
                entity.setPassword(rs.getString("password"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<User> getEntityList(String where) {
        List<User> reData = new ArrayList<User>();
        String sql = "select id,username,password,status,addTime from SG_Admin " + where;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                User entity = new User();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setUsername(list.get(i).get("username").toString());
                entity.setPassword(list.get(i).get("password").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insert(User entity) {
        String sql = "insert into SG_Admin(username, password, status, addTime) value(?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getUsername(), entity.getPassword(), Quantity.STATUS_ONE};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(User entity) {
        String sql = "update SG_Admin set username = ? , password = ? where id = ? ";
        Object [] params = new Object[]{entity.getUsername(), entity.getPassword(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_Admin set status = ? where id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public User users_exist(String username, String password) {
        String sql = "select id,username,password,status,addTime from SG_Admin where username = ? and password = ? and status > ? ";
        final Object [] params = new Object[]{username,password,Quantity.STATUS_ZERO};
        final User entity = new User();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setUsername(rs.getString("username"));
                entity.setPassword(rs.getString("password"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public User formEntity(HttpServletRequest req, int id) {
        User entity = new User();
        entity.setId(id);
        entity.setUsername(req.getParameter("username"));
        entity.setPassword(req.getParameter("password"));
        return entity;
    }

    @Override
    public List<Func> getMenuFunc(int adminId){
        List<Func> func_ls = new ArrayList<Func>();
        List<AdminRole> ars = adminRoleService.getAdminRole(adminId);
        if (ars.size() > 0){
            int role_id = ars.get(0).getRoleId();
            List<RoleFunc> rfs = roleFuncService.getRoleFunc(role_id);
            if(rfs.size() > 0){
                for (int i = 0; i < rfs.size(); i++) {
                    func_ls.add(funcService.get(rfs.get(i).getFuncId()));
                }
            }
        }

        return func_ls;
    }

    @Override
    public String getMenus(List<Func> func_ls,String menu_code) {
        String start_li = "<li>";
        String start_li_class = "<li class='"+ Menu.ACTIVE+"'>";
        String end_li = "</li>";
        StringBuffer sb = new StringBuffer();
        StringBuffer sy = new StringBuffer();
        StringBuffer sm = new StringBuffer();
        StringBuffer us = new StringBuffer();
        if (func_ls.size() > 0){
            if (menu_code.equals(Menu.BANNER_CODE) || menu_code.equals(Menu.EVENT_CODE) || menu_code.equals(Menu.ICON_CODE)){
                sy.append(start_li_class);
                sy.append("<a href='index.jsp#'><i class='fa fa-home'></i> <span class='nav-label'>首页配置</span><span class='fa arrow'></span></a>");
                sy.append("<ul class='nav nav-second-level'>");
            }else{
                sy.append(start_li_class);
                sy.append("<a href='index.jsp#'><i class='fa fa-home'></i> <span class='nav-label'>首页配置</span><span class='fa arrow'></span></a>");
                sy.append("<ul class='nav nav-second-level'>");
            }
            for (Func func : func_ls){
                if (func.getCode().equals(Menu.HOME_CODE)){
                    if (menu_code.equals(Menu.HOME_CODE)){
                        sb.append(start_li_class);
                        sb.append("<a href='${ctx}/user/index.do?uid=${user.id}'><i class='fa fa-th-large'></i> <span class='nav-label'>主页</span> </a>");
                        sb.append(end_li);
                    }else{
                        sb.append(start_li);
                        sb.append("<a href='${ctx}/user/index.do?uid=${user.id}'><i class='fa fa-th-large'></i> <span class='nav-label'>主页</span> </a>");
                        sb.append(end_li);
                    }
                }else if(func.getCode().equals(Menu.SUB_CODE)){
                    if (menu_code.equals(Menu.SUB_CODE)){
                        sb.append(start_li_class);
                        sb.append("<a href='${ctx}/sub/info.do?uid=${user.id}'><i class='fa fa-connectdevelop'></i> <span class='nav-label'>课程体系</span> </a>");
                        sb.append(end_li);
                    }else{
                        sb.append(start_li);
                        sb.append("<a href='${ctx}/sub/info.do?uid=${user.id}'><i class='fa fa-connectdevelop'></i> <span class='nav-label'>课程体系</span> </a>");
                        sb.append(end_li);
                    }
                }else if(func.getCode().equals(Menu.COURSE_CODE)){
                    if (menu_code.equals(Menu.COURSE_CODE)){
                        sb.append(start_li_class);
                        sb.append("<a href='${ctx}/course/info.do?uid=${user.id}'><i class='fa fa-leanpub'></i> <span class='nav-label'>课程内容</span> </a>");
                        sb.append(end_li);
                    }else{
                        sb.append(start_li);
                        sb.append("<a href='${ctx}/course/info.do?uid=${user.id}'><i class='fa fa-leanpub'></i> <span class='nav-label'>课程内容</span> </a>");
                        sb.append(end_li);
                    }
                }else if(func.getCode().equals(Menu.BANNER_CODE)){
                    if (menu_code.equals(Menu.BANNER_CODE)){
                        sy.append(start_li_class);
                        sy.append("<a href='${ctx}/course/info.do?uid=${user.id}'><i class='fa fa-star'></i> <span class='nav-label'>banner设置</span> </a>");
                        sb.append(end_li);
                    }else{
                        sy.append(start_li);
                        sy.append("<a href='${ctx}/course/info.do?uid=${user.id}'><i class='fa fa-star'></i> <span class='nav-label'>banner设置</span> </a>");
                        sy.append(end_li);
                    }
                }else if(func.getCode().equals(Menu.EVENT_CODE)){
                    if (menu_code.equals(Menu.EVENT_CODE)){
                        sy.append(start_li_class);
                        sy.append("<a href='${ctx}/event/info.do?uid=${user.id}'><i class='fa fa-globe'></i> <span class='nav-label'>event设置</span> </a>");
                        sb.append(end_li);
                    }else{
                        sy.append(start_li);
                        sy.append("<a href='${ctx}/event/info.do?uid=${user.id}'><i class='fa fa-globe'></i> <span class='nav-label'>event设置</span> </a>");
                        sy.append(end_li);
                    }
                }else if(func.getCode().equals(Menu.ICON_CODE)){
                    if (menu_code.equals(Menu.ICON_CODE)){
                        sy.append(start_li_class);
                        sy.append("<a href='${ctx}/icon/info.do?uid=${user.id}'><i class='fa fa-picture-o'></i> <span class='nav-label'>icon设置</span> </a>");
                        sb.append(end_li);
                    }else{
                        sy.append(start_li);
                        sy.append("<a href='${ctx}/icon/info.do?uid=${user.id}'><i class='fa fa-picture-o'></i> <span class='nav-label'>icon设置</span> </a>");
                        sy.append(end_li);
                    }
                }
            }
        }
        return null;
    }


}
