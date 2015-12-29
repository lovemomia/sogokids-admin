package com.sogokids.group.service.impl;

import com.sogokids.group.model.GroupCourse;
import com.sogokids.group.service.GroupCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/12/29.
 */
@Service
public class GroupCourseServiceImpl implements GroupCourseService {
    private final Logger log = LoggerFactory.getLogger(GroupUserServiceImpl.class);

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
        return null;
    }

    @Override
    public GroupCourse get(int id) {
        return null;
    }

    @Override
    public GroupCourse isget(int gid, int uid) {
        return null;
    }

    @Override
    public int insert(GroupCourse entity) {
        return 0;
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
        return null;
    }
}
