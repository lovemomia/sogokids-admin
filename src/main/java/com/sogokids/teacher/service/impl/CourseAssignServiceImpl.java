package com.sogokids.teacher.service.impl;

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

        String sql = "SELECT a.id,a.title,b.StartTime,b.EndTime,b.PlaceId,b.Id as skuId FROM SG_Course a,SG_CourseSku b where a.ParentId = 0 and a.status > 0 and a.status != 3 and b.status > 0 and b.status != 3 and a.id = b.CourseId and b.StartTime > " + startTime +" order by b.StartTime desc ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseAssign entity = new CourseAssign();
                int courseId = Integer.parseInt(list.get(i).get("id").toString());
                entity.setCourseId(courseId);
                entity.setCourseTitle(list.get(i).get("title").toString());
                int courseSkuId = Integer.parseInt(list.get(i).get("skuId").toString());
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

}