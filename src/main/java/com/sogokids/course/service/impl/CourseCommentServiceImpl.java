package com.sogokids.course.service.impl;

import com.sogokids.course.model.CourseComment;
import com.sogokids.course.service.CourseCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 16/2/19.
 */
@Service
public class CourseCommentServiceImpl implements CourseCommentService {

    private final Logger log = LoggerFactory.getLogger(CourseCommentServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CourseComment> getTCourseComments(int sub_id) {
        List<CourseComment> comments = new ArrayList<CourseComment>();
        String sql = "SELECT a.id,a.UserId,a.CourseId,a.Star,a.Content,a.addTime,b.KeyWord,d.NickName FROM SG_CourseComment a,SG_Course b,SG_Subject c,SG_User d where a.Status > 0 and b.Status > 0 and b.Status != 3 and c.Status > 0 and c.Status != 3 and d.Status > 0 and a.CourseId = b.Id and a.UserId = d.Id and b.SubjectId = c.Id and a.Recommended = 1 and c.Id = ? order by a.AddTime desc";
        Object [] params = new Object[]{sub_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseComment entity = new CourseComment();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setStar(Integer.parseInt(list.get(i).get("Star").toString()));
                entity.setContent(list.get(i).get("Content").toString());
                entity.setAddTime(list.get(i).get("AddTime").toString());
                entity.setKeyWord(list.get(i).get("KeyWord") == null?"":list.get(i).get("KeyWord").toString());
                entity.setNickName(list.get(i).get("NickName") == null ? "" : list.get(i).get("NickName").toString());

                comments.add(entity);
            }
        }
        return comments;
    }

    @Override
    public List<CourseComment> getNTCourseComments(int sub_id) {
        List<CourseComment> comments = new ArrayList<CourseComment>();
        String sql = "SELECT a.id,a.UserId,a.CourseId,a.Star,a.Content,a.addTime,b.KeyWord,d.NickName FROM SG_CourseComment a,SG_Course b,SG_Subject c,SG_User d where a.Status > 0 and b.Status > 0 and b.Status != 3 and c.Status > 0 and c.Status != 3 and d.Status > 0 and a.CourseId = b.Id and a.UserId = d.Id and b.SubjectId = c.Id and a.Recommended = 0 and c.Id = ? order by a.AddTime desc";
        Object [] params = new Object[]{sub_id};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if (list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CourseComment entity = new CourseComment();
                entity.setId(Integer.parseInt(list.get(i).get("Id").toString()));
                entity.setStar(Integer.parseInt(list.get(i).get("Star").toString()));
                entity.setContent(list.get(i).get("Content").toString());
                entity.setAddTime(list.get(i).get("AddTime").toString());
                entity.setKeyWord(list.get(i).get("KeyWord") == null?"":list.get(i).get("KeyWord").toString());
                entity.setNickName(list.get(i).get("NickName") == null ? "" : list.get(i).get("NickName").toString());

                comments.add(entity);
            }
        }
        return comments;
    }

    @Override
    public int update_Recommended(int id,int recommended) {
        String sql = "update SG_CourseComment set Recommended = ? where Id = ? ";
        Object [] params = new Object[]{recommended,id};
        int reData = 0;
        try{
            reData = jdbcTemplate.update(sql,params);
        }catch (Exception _ex){
            log.error("更新课程推荐 error:"+_ex.getMessage());
        }

        return reData;
    }
}
