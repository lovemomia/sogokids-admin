package com.sogokids.query.service.impl;

import com.sogokids.course.model.CourseSku;
import com.sogokids.query.model.CourseTimeQuery;
import com.sogokids.query.model.CourseUser;
import com.sogokids.query.model.MobileUser;
import com.sogokids.query.service.CourseTimeQueryService;
import com.sogokids.system.model.Place;
import com.sogokids.system.service.PlaceService;
import com.sogokids.utils.util.DateUtil;
import com.sogokids.utils.util.Quantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/12/10.
 */
@Service
public class CourseTimeQueryServiceImpl implements CourseTimeQueryService {

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

    /**
     * 按照时间和课程标题关键字进行课程查询
     * @param course_title
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<CourseTimeQuery> getCourseTimeQuerys(String course_title, String startDate, String endDate){
        List<CourseTimeQuery> reData = new ArrayList<CourseTimeQuery>();
        String where = "";
        if (!course_title.equals("") && course_title != null){
            where = where + " and a.Title LIKE '%" + course_title +"%' ";
        }
        if (!startDate.equals("") && startDate != null){
            startDate = startDate + " 00:00:00";
            where = where + " and b.StartTime >= " + "'" + startDate + "' ";
        }
        if (!endDate.equals("") && endDate != null){
            endDate = endDate + " 23:59:59";
            where = where + " and b.StartTime <= " + "'" + endDate + "' ";
        }
        String sql = "SELECT a.id,a.ParentId,a.SubjectId,a.Title,a.Cover,b.id as skuId,b.ParentId as skuParentId,b.CourseId,b.StartTime,b.EndTime,b.Deadline,b.PlaceId FROM SG_Course as a, SG_CourseSku as b  where a.Id = b.CourseId and a.Status > 0 and a.Status != 3 and a.ParentId = 0 and b.Status > 0 and b.Status != 3 " + where + " order by b.StartTime desc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseTimeQuery entity = new CourseTimeQuery();
                int course_id = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setCourseId(course_id);
                entity.setCourseTitle(list.get(i).get("Title").toString());
                entity.setCourseCover(list.get(i).get("Cover").toString());
                entity.setCourseParentId(Integer.parseInt(list.get(i).get("ParentId").toString()));
                entity.setSubjectId(Integer.parseInt(list.get(i).get("SubjectId").toString()));

                entity.setSkuId(Integer.parseInt(list.get(i).get("skuId").toString()));
                entity.setSkuParentId(Integer.parseInt(list.get(i).get("skuParentId").toString()));
                entity.setSkuCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setSkuPlaceId(Integer.parseInt(list.get(i).get("PlaceId").toString()));

                entity.setSkuStartTime(DateUtil.DateToStr_Cn(list.get(i).get("StartTime").toString()));
                entity.setSkuEndTime(list.get(i).get("EndTime").toString());
                entity.setSkuDeadline(list.get(i).get("Deadline").toString());

                Place place = placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString()));

                entity.setPlaceName(place.getAddress());
                reData.add(getCourseTimeQueryCount(entity));
            }
        }

        return reData;
    }

    /**
     * 根据课程和课程场次id获取报名人数
     * @param courseTimeQuery
     * @return
     */
    private CourseTimeQuery getCourseTimeQueryCount(CourseTimeQuery courseTimeQuery){
        int p_count = getCount(courseTimeQuery.getCourseId(), courseTimeQuery.getSkuId());//获取当前课程的报名总数量
        CourseSku courseSku = getSku(courseTimeQuery.getSkuId());//获取试听课的sku信息
        if (courseSku != null && courseSku.getId() != 0){
            p_count = p_count + getCount(courseSku.getCourseId(), courseSku.getId());
        }
        courseTimeQuery.setUserCount(p_count);

        return courseTimeQuery;
    }

    /**
     * 根据父级sku的ID 获取子级sku
     * @param skuParentId
     * @return
     */
    private CourseSku getSku(int skuParentId){
        String sql = "SELECT * FROM SG_CourseSku where ParentId = ? and Status > ? and Status != ? ";
        final Object [] params = new Object[]{skuParentId, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        final CourseSku entity = new CourseSku();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setCourseId(rs.getInt("CourseId"));
                entity.setParentId(rs.getInt("ParentId"));
                entity.setStartTime(rs.getString("StartTime"));
                entity.setEndTime(rs.getString("EndTime"));
                entity.setDeadline(rs.getString("Deadline"));
                entity.setStock(rs.getInt("Stock"));
                entity.setUnlockedStock(rs.getInt("UnlockedStock"));
                entity.setLockedStock(rs.getInt("LockedStock"));
                entity.setPlaceId(rs.getInt("PlaceId"));
                entity.setAdult(rs.getInt("Adult"));
                entity.setChild(rs.getInt("Child"));
                entity.setStatus(rs.getInt("Status"));
                entity.setAddTime(rs.getString("AddTime"));

            }
        });

        return entity;

    }

    /**
     * 根据子级课程和课程场次id获取报名人数
     * @param courseId
     * @param skuId
     * @return
     */
    private int getCount(int courseId, int skuId){
        String sql = "SELECT count(*) as userCount FROM SG_BookedCourse where Status > ? and CourseId = ? and CourseSkuId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, courseId, skuId};
        return Integer.parseInt(jdbcTemplate.queryForList(sql, params).get(0).get("userCount").toString());
    }


    /**
     * 根据课程和课程sku的Id得到用户信息集合,并把所属试听课的进行合并
     * @param courseId
     * @param skuId
     * @return
     */
    @Override
    public List<CourseUser> getCourseUsers(int courseId, int skuId){
        List<CourseUser> courseUsers = new ArrayList<CourseUser>();
        List<CourseUser> p_courseUsers = this.getSkuChildCourseUsers(Quantity.STATUS_ONE, courseId, skuId);
        courseUsers.addAll(p_courseUsers);
        CourseSku courseSku = this.getSku(skuId);
        if (courseSku.getId() > 0){
            int int_id = p_courseUsers.size() + 1;
            List<CourseUser> c_courseUsers = this.getSkuChildCourseUsers(int_id, courseSku.getCourseId(), courseSku.getId());
            courseUsers.addAll(c_courseUsers);
        }

        return courseUsers;
    }

    /**
     * 根据课程和课程sku的Id得到用户信息集合
     * @param courseId
     * @param skuId
     * @return
     */
    public List<CourseUser> getSkuChildCourseUsers(int int_id, int courseId, int skuId){
        List<CourseUser> courseUsers = new ArrayList<CourseUser>();
        String sql = "SELECT a.Id,a.UserId,a.OrderId,a.PackageId,a.CourseId,a.CourseSkuId,b.Mobile,b.NickName FROM SG_BookedCourse a,SG_User b where a.UserId = b.Id and a.Status > ? and b.Status > ? and a.CourseId = ? and a.CourseSkuId = ? ";
        Object [] params = new Object[]{Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, courseId, skuId};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseUser entity = new CourseUser();
                entity.setCode(int_id);
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setUserId(Integer.parseInt(list.get(i).get("UserId").toString()));
                entity.setOrderId(Integer.parseInt(list.get(i).get("OrderId").toString()));
                entity.setPackageId(Integer.parseInt(list.get(i).get("PackageId").toString()));
                entity.setCourseId(Integer.parseInt(list.get(i).get("CourseId").toString()));
                entity.setCourseSkuId(Integer.parseInt(list.get(i).get("CourseSkuId").toString()));
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setNickName(list.get(i).get("NickName").toString());

                courseUsers.add(getCourseUserOrigin(entity));

                int_id ++;

            }
        }

        return courseUsers;
    }

    /**
     * 计算购买来源
     * @param courseUser
     * @return
     */
    private CourseUser getCourseUserOrigin(CourseUser courseUser){
        String sql = "SELECT a.Id,b.id as s_skuid,b.SubjectId,b.CourseId,b.Price,c.Type,c.Title FROM SG_SubjectOrderPackage a, SG_SubjectSku b, SG_Subject c where a.Id = ? and a.SkuId = b.Id and b.SubjectId = c.Id and a.Status > ? and b.Status > ? and c.Status > ? and b.Status != ? ";
        Object [] params = new Object[]{courseUser.getPackageId(), Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                int type = Integer.parseInt(list.get(i).get("Type").toString());
                int courseId = Integer.parseInt(list.get(i).get("CourseId").toString());
//                BigDecimal price = new BigDecimal(list.get(i).get("Price").toString());
                String title = list.get(i).get("Title").toString();
                if (type == 2){
                    courseUser.setOrigin(title);
                }else{
                    if (courseId == 0){
                        courseUser.setOrigin(title+"课程体系");
                    }else{
                        courseUser.setOrigin("热门推荐");
                    }
                }

            }
        }

        return courseUser;

    }

    /**
     * 使用电话号码查询
     * @param mobile
     * @return
     */
    @Override
    public Map<String,Object> getMobileUsers(String mobile){
        Map<String,Object> map = new HashMap<String, Object>();
        List<MobileUser> reData = new ArrayList<MobileUser>();
        StringBuffer sb = new StringBuffer();
        sb.append(getStartTable());
        String where = "";
        if (!mobile.equals("") && mobile != null){
            where = where + " and a.Mobile = " + mobile;
        }
//        String sql = "SELECT c.NickName,a.Mobile,d.Title,b.PackageId,e.StartTime,e.PlaceId FROM SG_SubjectOrder a,SG_BookedCourse b, SG_User c, SG_Course d, SG_CourseSku e where a.id = b.OrderId and a.UserId = b.UserId and b.UserId = c.id and b.CourseId = d.Id and b.CourseSkuId = e.Id and a.Status > 0 and b.Status > 0 and c.Status > 0 and d.Status > 0 and e.Status > 0 and e.Status != 3 " + where;
        String sql = "SELECT c.NickName,a.Mobile,d.Title,b.PackageId,e.StartTime,e.PlaceId FROM SG_SubjectOrder a,SG_BookedCourse b, SG_User c, SG_Course d, SG_CourseSku e where a.id = b.OrderId and a.UserId = b.UserId and b.UserId = c.id and b.CourseId = d.Id and b.CourseSkuId = e.Id and a.Status > 0 and b.Status > 0 and c.Status > 0 and e.Status > 0 and e.Status != 3 " + where;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                MobileUser entity = new MobileUser();
                entity.setNickName(list.get(i).get("NickName").toString());
                entity.setMobile(list.get(i).get("Mobile").toString());
                entity.setTitle(list.get(i).get("Title").toString());
                entity.setPackageId(Integer.parseInt(list.get(i).get("PackageId").toString()));
                entity.setStartTime(DateUtil.DateToStr_Cn(list.get(i).get("StartTime").toString()));
                entity.setPlaceId(Integer.parseInt(list.get(i).get("PlaceId").toString()));

                Place place = placeService.get(Integer.parseInt(list.get(i).get("PlaceId").toString()));

                entity.setPlaceName(place.getAddress());
                entity = getMobileUserOrigin(entity);
                sb.append("<tr>");
                sb.append("<td>").append(entity.getNickName()).append("</td>");
                sb.append("<td>").append(entity.getMobile()).append("</td>");
                sb.append("<td>").append(entity.getTitle()).append("</td>");
                sb.append("<td>").append(entity.getStartTime()).append("</td>");
                sb.append("<td>").append(entity.getPlaceName()).append("</td>");
                sb.append("<td>").append(entity.getOrigin()).append("</td>");
                sb.append("</tr>");
                reData.add(entity);
            }
        }
        sb.append(getEndTable());
        map.put("list",reData);
        map.put("mobileHtml",sb.toString());

        return map;
    }

    /**
     * 手机号码查询－计算购买来源
     * @param mobileUser
     * @return
     */
    private MobileUser getMobileUserOrigin(MobileUser mobileUser){
        String sql = "SELECT a.Id,b.id as s_skuid,b.SubjectId,b.CourseId,b.Price,c.Type,c.Title FROM SG_SubjectOrderPackage a, SG_SubjectSku b, SG_Subject c where a.Id = ? and a.SkuId = b.Id and b.SubjectId = c.Id and a.Status > ? and b.Status > ? and c.Status > ? and b.Status != ? ";
        Object [] params = new Object[]{mobileUser.getPackageId(), Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_ZERO, Quantity.STATUS_THREE};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                int type = Integer.parseInt(list.get(i).get("Type").toString());
                int courseId = Integer.parseInt(list.get(i).get("CourseId").toString());
//                BigDecimal price = new BigDecimal(list.get(i).get("Price").toString());
                String title = list.get(i).get("Title").toString();
                if (type == 2){
                    mobileUser.setOrigin(title);
                }else{
                    if (courseId == 0){
                        mobileUser.setOrigin(title+"课程体系");
                    }else{
                        mobileUser.setOrigin("热门推荐");
                    }
                }

            }
        }

        return mobileUser;

    }

    private String getStartTable(){
        StringBuffer sb = new StringBuffer();

        sb.append("<table class=\"table table-striped table-bordered table-hover dataTables-example\">");
        sb.append("<thead>");
        sb.append("<tr class=\"gradeX\">");
        sb.append("<th>用户昵称</th>");
        sb.append("<th>联系方式</th>");
        sb.append("<th>所选课程</th>");
        sb.append("<th>开课时间</th>");
        sb.append("<th>上课地点</th>");
        sb.append("<th>购买来源</th>");
        sb.append("</tr>");
        sb.append("</thead>");
        sb.append("<tbody>");

        return sb.toString();
    }

    private String getEndTable() {
        StringBuffer sb = new StringBuffer();
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }

}
