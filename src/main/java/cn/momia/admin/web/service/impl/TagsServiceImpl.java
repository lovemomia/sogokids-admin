package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.entity.Product;
import cn.momia.admin.web.entity.Tags;
import cn.momia.admin.web.service.TagsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/7/14.
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tags> getEntitys() {
        List<Tags> reData = new ArrayList<Tags>();
        String sql = "select id,name from t_tag where status > ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                Tags entity = new Tags();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setName(list.get(i).get("name").toString());
                entity.setChecked(0);
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<Tags> getTags(String tags) {
        List<Tags> reData = this.getEntitys();
        if (tags.length() > 0 && reData.size() > 0){
            for (int i = 0; i < reData.size(); i++) {
                Tags tag = reData.get(i);
                String id = ""+tag.getId();
                if(tags.contains(id)){
                    tag.setChecked(1);
                }
            }

        }
        return reData;
    }
}
