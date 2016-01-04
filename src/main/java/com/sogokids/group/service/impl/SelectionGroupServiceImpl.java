package com.sogokids.group.service.impl;

import com.sogokids.group.model.GroupCourse;
import com.sogokids.group.model.GroupUser;
import com.sogokids.group.model.SelectionGroup;
import com.sogokids.group.service.GroupUserService;
import com.sogokids.group.service.SelectionGroupService;
import com.sogokids.query.model.Customer;
import com.sogokids.query.service.CustomerService;
import com.sogokids.system.model.Place;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/12/25.
 */
@Service
public class SelectionGroupServiceImpl implements SelectionGroupService {

    private final Logger log = LoggerFactory.getLogger(SelectionGroupServiceImpl.class);

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PlaceService placeService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SelectionGroup> getEntitys() {
        List<SelectionGroup> reData = new ArrayList<SelectionGroup>();
        String sql = "select Id,Name,Status,AddTime from SG_SelectionGroup where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                SelectionGroup entity = new SelectionGroup();
                int g_id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setId(g_id);
                entity.setName(list.get(i).get("Name").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());
                SelectionGroup selectionGroup = this.get(g_id);
                if (selectionGroup != null || selectionGroup.getId() > 0){
                    entity.setXkFlag(1);
                }

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public SelectionGroup get(int id) {
        String sql = "select Id,Name,Status,AddTime from SG_SelectionGroup where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final SelectionGroup entity = new SelectionGroup();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("Name"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(SelectionGroup entity) {
        String sql = "insert into SG_SelectionGroup(Name,Status,AddTime) value(?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getName(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            log.info("开始添加分组信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束添加分组信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int insertKey(SelectionGroup selectionGroup) {
        final SelectionGroup entity = selectionGroup;
        final String sql = "insert into SG_SelectionGroup(Name,Status,AddTime) value(?, ?, NOW()) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = 0;
        try {
            log.info("开始添加分组信息-返回自增ID!");
            reData = jdbcTemplate.update(new PreparedStatementCreator() {
                public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {

                    int i = 0;
                    java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(++i, entity.getName());
                    ps.setInt(++i, Quantity.STATUS_ONE);

                    return ps;
                }
            }, keyHolder);
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        log.info("结束添加分组信息-返回自增ID!");

        return reData;
    }

    @Override
    public int update(SelectionGroup entity) {
        String sql = "update SG_SelectionGroup set Name = ? where Id = ? ";
        Object [] params = new Object[]{entity.getName(), entity.getId()};
        int reData = 0;
        try{
            log.info("开始修改分组信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束修改分组信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update SG_SelectionGroup set Status = ? where Id = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO,id};
        log.info("开始删除分组信息!");
        int reData = jdbcTemplate.update(sql,params);
        log.info("结束删除分组信息!");
        return reData;
    }

    @Override
    public SelectionGroup formEntity(HttpServletRequest request, int id) {
        SelectionGroup entity = new SelectionGroup();

        entity.setId(id);
        entity.setName(request.getParameter("upName"));

        return entity;
    }

    @Override
    public String addGroup(HttpServletRequest req){
        String reData = "";

        log.info("addGroup:开始添加分组信息!");
        SelectionGroup selectionGroup = new SelectionGroup();
        String name = req.getParameter("name");
        selectionGroup.setName(name);
        int gid = this.insertKey(selectionGroup);
        log.info("addGroup:结束添加分组信息,新增id:" + gid + ".");
        String mobiles = req.getParameter("mobiles");
        log.info("addGroup:开始添加分组用户信息!");
        reData = addGroupUser(gid, mobiles);
        log.info("addGroup:结束添加分组用户信息!");

        return reData;
    }

    @Override
    public String addGroupUser(int gid, String mobiles){
        StringBuffer sb = new StringBuffer();
        String[] userMobiles = null;
        if (mobiles != null || !mobiles.equals("")) {
            if (mobiles.indexOf(",") > 0) {
                userMobiles = mobiles.split(",");
            }
        }

        if (userMobiles != null) {
            for (int i = 0; i < userMobiles.length; i++) {
                Customer customer = customerService.getCustomerByMobile(userMobiles[i]);
                if (customer.getId() > 0) {
                    if (groupUserService.isget(gid,customer.getId()).getId() > 0) {
                        sb.append(userMobiles[i]).append(",");
                    }else{
                        GroupUser groupUser = new GroupUser();
                        groupUser.setGroupId(gid);
                        groupUser.setUserId(customer.getId());
                        groupUserService.insert(groupUser);
                    }
                } else {
                    sb.append(userMobiles[i]).append(",");
                }
            }
        }else{
            Customer customer = customerService.getCustomerByMobile(mobiles);
            if (customer.getId() > 0) {
                if (groupUserService.isget(gid,customer.getId()).getId() > 0) {
                    sb.append(mobiles);
                }else{
                    GroupUser groupUser = new GroupUser();
                    groupUser.setGroupId(gid);
                    groupUser.setUserId(customer.getId());
                    groupUserService.insert(groupUser);
                }
            } else {
                sb.append(mobiles);
            }
        }

        return sb.toString();

    }

    @Override
    public List<Customer> getGroupCustomers(int gId,String where) {
        List<Customer> reData = new ArrayList<Customer>();
        String sql = "SELECT @row := @row+1 as rowId,b.Id,c.Id as userId,c.NickName,c.Mobile FROM sogokids.SG_SelectionGroup a,sogokids.SG_GroupUser b, sogokids.SG_User c,(select @row := 0) d where a.Status > 0 and b.Status > 0 and c.Status > 0 and a.Id = b.GroupId and b.UserId = c.Id and a.Id = ? " + where;
        Object [] params = new Object[]{gId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Customer entity = new Customer();
                entity.setRowId(Integer.parseInt(list.get(i).get("rowId").toString()));
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setNickName(list.get(i).get("NickName").toString());
                entity.setMobile(list.get(i).get("Mobile").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<GroupCourse> getGroupCourses(String title, String dateTime) {
        String where = "";
        String startTime = "";
        String endTime = "";
        if (!dateTime.equals("") && dateTime != null){
            startTime = dateTime + " 00:00:00";
            where = where + " and a.StartTime >= " + "'" + startTime + "'";
            endTime = dateTime + " 23:59:59";
            where = where + " and a.StartTime <= " + "'" + endTime + "'";
        }
        if (!title.equals("") && title != null){
            where = where + " and b.Title LIKE '%" + title +"%'";
        }
        List<GroupCourse> reData = new ArrayList<GroupCourse>();
        String sql = "SELECT a.Id as skuId,a.StartTime,a.PlaceId,b.Id as courseId,b.Title,b.Cover FROM sogokids.SG_CourseSku a,sogokids.SG_Course b where a.status > 0 and a.status != 3 and b.status > 0 and b.status != 3 and a.CourseId = b.Id and a.ParentId = 0 " + where + " order by a.StartTime desc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                GroupCourse entity = new GroupCourse();
                entity.setCourseSkuId(Integer.parseInt(list.get(i).get("skuId").toString()));
                entity.setCourseSkuStartTime(DateUtil.DateToStr_Cn(list.get(i).get("StartTime").toString()));
                Place place = placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString()));
                entity.setCourseSkuPlace(place.getAddress());
                entity.setCourseId(Integer.parseInt(list.get(i).get("courseId").toString()));
                entity.setCourseTitle(list.get(i).get("Title").toString());
                entity.setCourseCover(list.get(i).get("Cover").toString());

                reData.add(entity);
            }
        }

        return reData;
    }
}
