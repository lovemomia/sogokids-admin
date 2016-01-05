package com.sogokids.group.service.impl;

import cn.momia.common.config.Configuration;
import com.alibaba.fastjson.JSONObject;
import com.sogokids.course.model.CourseSku;
import com.sogokids.course.service.CourseSkuService;
import com.sogokids.group.model.GroupCourse;
import com.sogokids.group.model.GroupUser;
import com.sogokids.group.service.GroupCourseService;
import com.sogokids.group.service.GroupUserService;
import com.sogokids.http.model.HttpResult;
import com.sogokids.http.service.HttpClientService;
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
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/12/29.
 */
@Service
public class GroupCourseServiceImpl implements GroupCourseService {

    private final Logger log = LoggerFactory.getLogger(GroupCourseServiceImpl.class);

    @Autowired
    private CourseSkuService courseSkuService;

    @Autowired
    private GroupUserService groupUserService;

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
    public List<GroupCourse> getEntitys() {
        List<GroupCourse> reData = new ArrayList<GroupCourse>();
        String sql = "select Id,GroupId,CourseId,CourseSkuId,Status,AddTime from SG_GroupCourse where Status > ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                GroupCourse entity = new GroupCourse();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setGroupId(Integer.parseInt(list.get(i).get("GroupId").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setCourseSkuId(Integer.parseInt(list.get(i).get("CourseSkuId").toString()));
                entity.setStatus(Integer.parseInt(list.get(i).get("Status").toString()));
                entity.setAddTime(list.get(i).get("AddTime").toString());

                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public GroupCourse get(int id) {
        String sql = "select Id,GroupId,CourseId,CourseSkuId,Status,AddTime from SG_GroupCourse where Id = ? and Status > ? ";
        final Object [] params = new Object[]{id, Quantity.STATUS_ZERO};
        final GroupCourse entity = new GroupCourse();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setGroupId(rs.getInt("GroupId"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setCourseSkuId(rs.getInt("CourseSkuId"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public GroupCourse isget(int gid) {
        String sql = "select Id,GroupId,CourseId,CourseSkuId,Status,AddTime from SG_GroupCourse where GroupId = ? and Status > ? ";
        final Object [] params = new Object[]{gid, Quantity.STATUS_ZERO};
        final GroupCourse entity = new GroupCourse();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setGroupId(rs.getInt("GroupId"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setCourseSkuId(rs.getInt("CourseSkuId"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));
            }
        });

        return entity;
    }

    @Override
    public int insert(GroupCourse entity) {
        String sql = "insert into SG_GroupCourse(GroupId,CourseId,CourseSkuId,Status,AddTime) value(?, ?, ?, ?, NOW()) ";
        Object [] params = new Object[]{entity.getGroupId(), entity.getCourseId(), entity.getCourseSkuId(), Quantity.STATUS_ONE};
        int reData = 0;
        try{
            log.info("开始添加分组选课信息!");
            reData = jdbcTemplate.update(sql,params);
            log.info("结束添加分组选课信息!");
        }catch (Exception _ex){
            _ex.printStackTrace();
        }
        return reData;
    }

    @Override
    public int update(GroupCourse entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public GroupCourse formEntity(HttpServletRequest request, int id) {
        GroupCourse entity = new GroupCourse();

        entity.setId(id);
        entity.setGroupId(Integer.parseInt(request.getParameter("groupId")));
        entity.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        entity.setCourseSkuId(Integer.parseInt(request.getParameter("courseSkuId")));

        return entity;
    }

    @Override
    public HttpResult insertGroupCourse(int gId, int skuId){
        HttpResult result = new HttpResult();
        CourseSku courseSku = courseSkuService.get(skuId);
        int courseId = courseSku.getCourseId();
        List<GroupUser> groupUsers = groupUserService.getGroupUsersByGId(gId);
        if (groupUsers.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (GroupUser groupUser : groupUsers) {
                sb.append(groupUser.getUserId()).append(",");
            }
            String user_ids = sb.toString();
            user_ids = user_ids.substring(0, user_ids.length()-1);
            String upload_qz_url = configuration.getString(Quantity.UPLOAD_GROUP_COURSE);

            StringBuffer sb_str = new StringBuffer();
            sb_str.append("coid=").append(courseId);
            long expired = DateUtil.getDateExpired(configuration.getInt(Quantity.SERVICE_PORT_TIME));//请求有效期
            sb_str.append("expired=").append(expired);
            sb_str.append("sid=").append(skuId);
            sb_str.append("uids=").append(user_ids);
            sb_str.append("key=").append(configuration.getString(Quantity.SERVICE_PORT_KEY));

            System.out.print("\r\n"+"sb_str:"+sb_str.toString());
            String sign = StringUtil.getSign(sb_str.toString());//请求加密串
            System.out.print("\r\n"+"sign:"+sign);

            String param = "coid="+courseId+"&expired="+expired+"&sid="+skuId+"&uids="+user_ids+"&sign="+sign;
            JSONObject jSONObject = httpClientService.httpPost(upload_qz_url,param);
            result = CastUtil.toObject(jSONObject, HttpResult.class);
            if (result.getErrno() == 0){
                GroupCourse groupCourse = new GroupCourse();
                groupCourse.setGroupId(gId);
                groupCourse.setCourseId(courseId);
                groupCourse.setCourseSkuId(skuId);
                this.insert(groupCourse);
            }
        }else{
            result.setErrno(1001);
        }

        return result;
    }

}
