package cn.momia.admin.web.service.impl;

import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.entity.Place;
import cn.momia.admin.web.entity.ProductContent;
import cn.momia.admin.web.service.ProductContentService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hoze on 15/7/23.
 */
@Service
public class ProductContentServiceImpl implements ProductContentService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ProductContent get(int id) {
        String sql = "select id,title,style,productId,status,addTime from t_product_content where id = ? and status > ? ";
        final Object [] params = new Object[]{id, FinalUtil.DEL_STATUS};
        final ProductContent entity = new ProductContent();
        jdbcTemplate.query(sql,params, new RowCallbackHandler(){
            public void processRow(ResultSet rs) throws SQLException {
                entity.setId(rs.getInt("id"));
                entity.setProductId(rs.getInt("productId"));
                entity.setTitle(rs.getString("title"));
                entity.setStyle(rs.getString("style"));
                entity.setStatus(rs.getInt("status"));
                entity.setAddTime(rs.getString("addTime"));
            }
        });

        return entity;
    }

    @Override
    public List<ProductContent> getEntitys() {
        List<ProductContent> reData = new ArrayList<ProductContent>();
        String sql = "select id,title,style,productId,status,addTime from t_product_content where status > ? order by id desc";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ProductContent entity = new ProductContent();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setStyle(list.get(i).get("style").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public List<ProductContent> getEntitysByProductId(int productId,String sort) {
        List<ProductContent> reData = new ArrayList<ProductContent>();
        String sql = "select id,title,style,productId,status,addTime from t_product_content where productId = ? and status > ? order by id "+sort;
        Object [] params = new Object[]{productId,FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ProductContent entity = new ProductContent();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setStyle(list.get(i).get("style").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }

    @Override
    public int insertKey(ProductContent entity) {
        final ProductContent content = entity;
        final String sql = "insert into t_product_content (title, style, productId, status, addTime) values (?, ?, ?, ?, NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int reData = jdbcTemplate.update( new PreparedStatementCreator(){
            public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException{
                int i = 0;
                java.sql.PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(++i, content.getTitle());
                ps.setString(++i, content.getStyle());
                ps.setInt(++i, content.getProductId());
                ps.setInt(++i, FinalUtil.ADD_STATUS);
                //ps.setString(++i,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                return ps;
            }
        },keyHolder);
        if (reData > 0) {
            reData = keyHolder.getKey().intValue();
        }
        return reData;

    }

    @Override
    public int insert(ProductContent entity) {
        String sql = "insert into t_product_content(title, style, productId, status, addTime) value(?, ?, ?, ?,NOW()) ";
        Object [] params = new Object[]{entity.getTitle(), entity.getStyle(), entity.getProductId(), FinalUtil.ADD_STATUS};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int update(ProductContent entity) {
        String sql = "update t_product_content set title = ?, style = ?, productId = ? where id = ? ";
        Object [] params = new Object[]{entity.getTitle(), entity.getStyle(), entity.getProductId(), entity.getId()};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public int delete(int id) {
        String sql = "update t_product_content set status = ? where id = ? ";
        Object [] params = new Object[]{FinalUtil.DEL_STATUS,id};
        int reData = jdbcTemplate.update(sql,params);
        return reData;
    }

    @Override
    public ProductContent formEntity(HttpServletRequest request, int id) {
        ProductContent entity = new ProductContent();
        entity.setId(id);
        entity.setProductId(Integer.parseInt(request.getParameter("productId")));
        entity.setTitle(request.getParameter("title"));
        entity.setStyle(request.getParameter("style"));
        return entity;
    }

    /**
     * 分页方法
     * @param start_row
     * @param end_row
     * @return
     */
    @Override
    public List<ProductContent> getQueryPages(int start_row,int end_row) {
        List<ProductContent> reData = new ArrayList<ProductContent>();
        String sql = "select id,title,style,productId,status,addTime from t_product_content where status > ? order by id desc limit "+start_row+","+end_row;
        Object [] params = new Object[]{FinalUtil.DEL_STATUS};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, params);
        if(list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                ProductContent entity = new ProductContent();
                entity.setId(Integer.parseInt(list.get(i).get("id").toString()));
                entity.setProductId(Integer.parseInt(list.get(i).get("productId").toString()));
                entity.setTitle(list.get(i).get("title").toString());
                entity.setStyle(list.get(i).get("style").toString());
                entity.setStatus(Integer.parseInt(list.get(i).get("status").toString()));
                entity.setAddTime(list.get(i).get("addTime").toString());
                reData.add(entity);
            }
        }

        return reData;
    }
}
