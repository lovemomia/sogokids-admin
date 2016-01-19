package com.sogokids.teacher.service.impl;

import com.sogokids.course.model.Course;
import com.sogokids.course.service.CourseService;
import com.sogokids.system.model.PlaceImg;
import com.sogokids.system.service.PlaceService;
import com.sogokids.teacher.model.CourseAssign;
import com.sogokids.teacher.service.CourseAssignService;
import com.sogokids.utils.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/1/7.
 */
@Service
public class CourseAssignServiceImpl implements CourseAssignService {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CourseService courseService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String,List<CourseAssign>> getCourseAssigns(){
        Map<String,List<CourseAssign>> map = new HashMap<String, List<CourseAssign>>();
        List<CourseAssign> courseAssigns = new ArrayList<CourseAssign>();
        List<CourseAssign> w_courseAssigns = new ArrayList<CourseAssign>();
        String startTime = "'"+DateUtil.NowDateStr()+"'";
        String sql = "SELECT StartTime,EndTime,PlaceId,Id,CourseId FROM SG_CourseSku where Status > 0 and Status != 0 and ParentId = 0 and StartTime > " + startTime +" order by StartTime desc ";
//        String sql = "SELECT a.id,a.title,b.StartTime,b.EndTime,b.PlaceId,b.Id as skuId FROM SG_Course a,SG_CourseSku b where a.ParentId = 0 and a.status > 0 and a.status != 3 and b.status > 0 and b.status != 3 and a.id = b.CourseId and b.StartTime > " + startTime +" order by b.StartTime desc ";
//        System.out.print(sql);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseAssign entity = new CourseAssign();

                int courseId = Integer.parseInt(list.get(i).get("CourseId").toString());
                Course course = courseService.get(courseId);
                entity.setCourseId(courseId);
                entity.setCourseTitle(course.getTitle());
                int courseSkuId = Integer.parseInt(list.get(i).get("Id").toString());
                entity.setSkuId(courseSkuId);
                String skuStartTime = list.get(i).get("StartTime").toString();
                entity.setSkuStartTime(DateUtil.getDateTimeStr_cn(DateUtil.StrToDate(skuStartTime)));
                String skuEndTime = list.get(i).get("EndTime").toString();
                entity.setSkuEndTime(DateUtil.getTimeStr_cn(DateUtil.StrToDate(skuEndTime)));
                int placeId = Integer.parseInt(list.get(i).get("placeId").toString());
                entity.setPlaceId(placeId);
                entity.setPlaceAddress(placeService.get(placeId).getAddress());
                if (isAssign(courseId, courseSkuId) == 0) {
                    w_courseAssigns.add(entity);
                }else{
                    courseAssigns.add(entity);
                }
            }
        }

        map.put("w_assign",w_courseAssigns);
        map.put("y_assign",courseAssigns);

        return map;

    }

    private int isAssign(int courseId, int courseSkuId){
        int reDate = 0;
        String sql = "SELECT * FROM SG_CourseTeacher where status > 0 and CourseId = "+courseId+" and CourseSkuId = "+courseSkuId;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if (list.size() > 0){
            reDate = 1;
        }

        return reDate;
    }

    @Override
    public List<CourseAssign> getTeacherCourses(int teacher_id){
        List<CourseAssign> courseAssigns = new ArrayList<CourseAssign>();
        String sql = "SELECT a.Id,b.title,b.cover,c.StartTime,c.EndTime,c.PlaceId FROM SG_CourseTeacher a,SG_Course b,SG_CourseSku c where a.status > 0 and b.status > 0 and b.status != 3 and c.status > 0 and c.status != 0 and b.Id = a.CourseId and c.Id = a.CourseSkuId and a.TeacherId = "+teacher_id+" order by c.StartTime desc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < list.size(); i++) {
            CourseAssign entity = new CourseAssign();
//            int courseId = Integer.parseInt(list.get(i).get("id").toString());
//            entity.setCourseId(courseId);
            entity.setCourseTitle(list.get(i).get("title").toString());
            entity.setCourseCover(list.get(i).get("cover").toString());
//            int courseSkuId = Integer.parseInt(list.get(i).get("skuId").toString());
//            entity.setSkuId(courseSkuId);
            String skuStartTime = list.get(i).get("StartTime").toString();
            entity.setSkuStartTime(DateUtil.getDateTimeStr_cn(DateUtil.StrToDate(skuStartTime)));
            String skuEndTime = list.get(i).get("EndTime").toString();
            entity.setSkuEndTime(DateUtil.getTimeStr_cn(DateUtil.StrToDate(skuEndTime)));
            int placeId = Integer.parseInt(list.get(i).get("placeId").toString());
            entity.setPlaceId(placeId);
            entity.setPlaceAddress(placeService.get(placeId).getAddress());
            courseAssigns.add(entity);
        }

        return courseAssigns;
    }

}
