package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.ProductContent;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/7/23.
 */
public interface ProductContentService {
    public ProductContent get(int id);
    public List<ProductContent> getEntitys();
    public List<ProductContent> getEntitysByProductId(int productId,String sort);
    public int insert(ProductContent entity);
    public int insertKey(ProductContent entity);
    public int update(ProductContent entity);
    public int delete(int id);
    public ProductContent formEntity(HttpServletRequest request,int id);
    public List<ProductContent> getQueryPages(int start_row,int end_row);
}
