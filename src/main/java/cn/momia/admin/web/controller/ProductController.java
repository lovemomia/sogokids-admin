package cn.momia.admin.web.controller;

import cn.momia.admin.web.common.FileUtil;
import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.common.PageTypeUtil;
import cn.momia.admin.web.entity.Images;
import cn.momia.admin.web.service.AdminUserService;
import cn.momia.admin.web.service.CategoryService;
import cn.momia.admin.web.service.CityService;
import cn.momia.admin.web.service.ContentBodyService;
import cn.momia.admin.web.service.ImagesService;
import cn.momia.admin.web.service.PlaceService;
import cn.momia.admin.web.service.ProductContentService;
import cn.momia.admin.web.service.ProductImgService;
import cn.momia.admin.web.service.ProductService;
import cn.momia.admin.web.service.QueryPageService;
import cn.momia.admin.web.service.TagsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoze on 15/6/15.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private QueryPageService queryPageService;

    @Autowired
    private ProductContentService productContentService;

    @Autowired
    private ContentBodyService contentBodyService;

    @RequestMapping("/info")
    public ModelAndView getEntitys(@RequestParam("uid") int uid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("mark") int mark, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        String reStr = FileUtil.PRODUCT_EDIT;
        if (mark == FinalUtil.PRODUCT_MARK_0) {//添加
            reStr = FileUtil.PRODUCT_ADD;
            context.put("tags", tagsService.getEntitys());
        } else if (mark == FinalUtil.PRODUCT_MARK_1) {//图片上传
            reStr = FileUtil.PRODUCT_IMG;
            context.put(FinalUtil.ENTITY, productService.get(id));
            context.put(FinalUtil.ENTITYS, productImgService.getEntitysByKey(id));
        } else if (mark == FinalUtil.PRODUCT_MARK_2) {//添加标签内容
            reStr = FileUtil.PRODUCT_OTHER_ADD;
            context.put(FinalUtil.ENTITY, productService.get(id));
        } else if (mark == FinalUtil.PRODUCT_MARK_3) {//修改标签内容
            reStr = FileUtil.PRODUCT_OTHER_EDIT;
            String content = productService.get(id).getContent();
            if (!content.equals("")) {
                context.put("contents", productService.getContentJsontoMap(content));
            }
            context.put(FinalUtil.ENTITY, productService.get(id));
        } else if (mark == FinalUtil.PRODUCT_MARK_4) {//预览
            reStr = FileUtil.PRODUCT_PREVIEW;
            context.put("jsonStr", productService.getPreviewInfo(id));
            context.put(FinalUtil.ENTITY, productService.get(id));
        }else if (mark == FinalUtil.PRODUCT_MARK_5) {//添加标签
            reStr = FileUtil.PRODUCT_CONTENT;
            productService.updateData(id);
            context.put("product",productService.get(id));
            context.put(FinalUtil.ENTITYS, productContentService.getEntitysByProductId(id,FinalUtil.SQL_QUERY_SORT_DESC));
        } else {//修改
            context.put(FinalUtil.ENTITY, productService.get(id));
            context.put("tags", tagsService.getTags(productService.get(id).getTags()));
        }
        context.put("pageNo", pageNo);
        context.put("categorys", categoryService.getEntitys());
        context.put("places", placeService.getEntitys());
        context.put("citys", cityService.getEntitys());
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(reStr, context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productService.insert(productService.formEntity(req, FinalUtil.ADD_INFO));
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "添加商品数据成功!");
            LOGGER.info("Add Product Info:" + "add Product success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "添加商品数据失败!");
            LOGGER.info("Add Product Info: " + "add Product failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productService.update(productService.formEntity(req, id));
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "修改商品数据成功!");
            LOGGER.info("Edit Product Info:" + "edit Product success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "修改商品数据失败!");
            LOGGER.info("Edit Product Info: " + "edit Product failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productService.delete(id);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "删除商品数据成功!");
            LOGGER.info("Delete Product Info:" + "delete Product success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "删除商品数据失败!");
            LOGGER.info("Delete Product Info: " + "delete Product failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/addimg")
    public ModelAndView addimg(@RequestParam("uid") int uid, @RequestParam("pid") int pid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();

        Images image = imagesService.uploadImg(req, 0, "");
        int reDate = productImgService.insert(productImgService.formEntity(image, pid));
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "添加商品图片数据成功!");
            LOGGER.info("Add Product Info:" + "add Product Image success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "添加商品图片数据失败!");
            LOGGER.info("Add Product Info: " + "add Product Image failure!");
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.ENTITYS, productImgService.getEntitysByKey(pid));
        context.put(FinalUtil.ENTITY, productService.get(pid));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_IMG, context);
    }

    @RequestMapping("/delimg")
    public ModelAndView delimg(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pid") int pid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productImgService.delete(id);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "删除商品图片数据成功!");
            LOGGER.info("Delete Product Info:" + "delete Product Image success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "删除商品图片数据失败!");
            LOGGER.info("Delete Product Image Info: " + "delete Product Image failure!");
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.ENTITYS, productImgService.getEntitysByKey(pid));
        context.put(FinalUtil.ENTITY, productService.get(pid));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_IMG, context);
    }

    @RequestMapping("/addcontent")
    public ModelAndView addcontent(@RequestParam("uid") int uid, @RequestParam("pid") int pid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        String contentJson = productService.getContentJsonStr(req, productService.get(pid).getContent(), 1);
        int reDate = productService.update_content(pid, contentJson);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据成功!");
            LOGGER.info("Add Product Content Info:" + "add product content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据失败!");
            LOGGER.info("Add Product Content Info: " + "add product content failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/upcontent")
    public ModelAndView upcontent(@RequestParam("uid") int uid, @RequestParam("pid") int pid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        String contentJson = productService.getContentJsonStr(req, productService.get(pid).getContent(), 0);
        int reDate = productService.update_content(pid, contentJson);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "修改商品内容数据成功!");
            LOGGER.info("Edit Product Content Info:" + "edit product content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "修改商品内容数据失败!");
            LOGGER.info("Edit Product Content Info: " + "edit product content failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/release")
    public ModelAndView release(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productService.update_releaseOroffline(id, FinalUtil.RELEASE_STATUS);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "发布活动数据成功!");
            LOGGER.info("Release Product Info:" + "release product success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "发布活动数据失败!");
            LOGGER.info("Release Product Info: " + "release product failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/offline")
    public ModelAndView offline(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productService.update_releaseOroffline(id, FinalUtil.OFFLINE_STATUS);
        if (reDate > 0) {
            context.put(FinalUtil.RETURN_MSG, "下线活动数据成功!");
            LOGGER.info("Offline Product Info:" + "offline product success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "下线活动数据失败!");
            LOGGER.info("Offline Product Info: " + "offline product failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_4, pageNo)));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT, context);
    }

    @RequestMapping("/opercon")
    public ModelAndView opercon(@RequestParam("uid") int uid, @RequestParam("pid") int pid, @RequestParam("id") int id, @RequestParam("mark") int mark, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        String reStr = FileUtil.PRODUCT_CON_EDIT;
        if (mark == 0) {
            reStr = FileUtil.PRODUCT_CONTENT;
            context.put(FinalUtil.ENTITYS,productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_DESC));
            context.put("product", productService.get(pid));
        }else if (mark == 1){//添加内容
            reStr = FileUtil.PRODUCT_CON_BODY;
            context.put(FinalUtil.ENTITYS, contentBodyService.getEntitysByContentId(id,FinalUtil.SQL_QUERY_SORT_DESC));
            context.put("product", productService.get(pid));
            context.put("content",contentBodyService.get(id));
        }else{//修改标签
            context.put(FinalUtil.ENTITY, productContentService.get(id));
            context.put("product", productService.get(pid));
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(reStr, context);
    }

    @RequestMapping("/addcon")
    public ModelAndView addcon(@RequestParam("uid") int uid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        int pid = Integer.parseInt(req.getParameter("productId"));
        int reDate = productContentService.insert(productContentService.formEntity(req,0));
        if (reDate > 0) {
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据成功!");
            LOGGER.info("Add Product Content Info:" + "add product content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据失败!");
            LOGGER.info("Add Product Content Info: " + "add product content failure!");
        }
        context.put("pageNo", pageNo);
        context.put("product",productService.get(pid));
        context.put(FinalUtil.ENTITYS, productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CONTENT, context);
    }

    @RequestMapping("/editcon")
    public ModelAndView editcon(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pageNo") int pageNo, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int pid = Integer.parseInt(req.getParameter("productId"));
        int reDate = productContentService.update(productContentService.formEntity(req,id));
        if (reDate > 0){
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG,"修改商品内容数据成功!");
            LOGGER.info("Edit Product Content Info:" + "edit product content success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"修改商品内容数据失败!");
            LOGGER.info("Edit Product Content Info: " + "edit product content failure!");
        }
        context.put("pageNo", pageNo);
        context.put("product",productService.get(pid));
        context.put(FinalUtil.ENTITYS,productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CONTENT,context);
    }

    @RequestMapping("/delcon")
    public ModelAndView delcon(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pid") int pid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = productContentService.delete(id);
        if (reDate > 0) {
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG, "删除商品内容数据成功!");
            LOGGER.info("Delete Product Content Info:" + "delete Product Content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "删除商品内容数据失败!");
            LOGGER.info("Delete Product Content Info: " + "delete Product Content failure!");
        }
        context.put("pageNo", pageNo);
        context.put("product",productService.get(pid));
        context.put(FinalUtil.ENTITYS,productContentService.getEntitysByProductId(pid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CONTENT, context);
    }

    @RequestMapping("/operbody")
    public ModelAndView operbody(@RequestParam("uid") int uid, @RequestParam("pid") int pid, @RequestParam("cid") int cid , @RequestParam("id") int id, @RequestParam("mark") int mark, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        String reStr = FileUtil.PRODUCT_BODY_EDIT;
        context.put("pageNo", pageNo);
        context.put("product", productService.get(pid));
        context.put("content",contentBodyService.get(cid));
        context.put(FinalUtil.ENTITY,contentBodyService.get(id));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(reStr, context);
    }

    @RequestMapping("/addbody")
    public ModelAndView addbody(@RequestParam("uid") int uid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {
        Map<String, Object> context = new HashMap<String, Object>();
        int pid = Integer.parseInt(req.getParameter("productId"));
        int cid = Integer.parseInt(req.getParameter("contentId"));

        int reDate = contentBodyService.insert(contentBodyService.formEntity2(req));
        if (reDate > 0) {
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据成功!");
            LOGGER.info("Add Product Content Info:" + "add product content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "添加商品内容数据失败!");
            LOGGER.info("Add Product Content Info: " + "add product content failure!");
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.ENTITYS,contentBodyService.getEntitysByContentId(cid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put("product", productService.get(pid));
        context.put("content",contentBodyService.get(cid));
        context.put(FinalUtil.USER_ENTITY, adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CON_BODY, context);
    }

    @RequestMapping("/editbody")
    public ModelAndView editbody(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pageNo") int pageNo, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        int pid = Integer.parseInt(req.getParameter("productId"));
        int cid = Integer.parseInt(req.getParameter("contentId"));
        int reDate = contentBodyService.update(contentBodyService.formEntity(req,id));
        if (reDate > 0){
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG,"修改商品内容数据成功!");
            LOGGER.info("Edit Product Content Info:" + "edit product content success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"修改商品内容数据失败!");
            LOGGER.info("Edit Product Content Info: " + "edit product content failure!");
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.ENTITYS,contentBodyService.getEntitysByContentId(cid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put("product", productService.get(pid));
        context.put("content",contentBodyService.get(cid));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CON_BODY,context);
    }

    @RequestMapping("/delbody")
    public ModelAndView delbody(@RequestParam("uid") int uid, @RequestParam("id") int id, @RequestParam("pid") int pid, @RequestParam("cid") int cid, @RequestParam("pageNo") int pageNo, HttpServletRequest req) {

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = contentBodyService.delete(id);
        if (reDate > 0) {
            productService.updateContentJson(pid);
            context.put(FinalUtil.RETURN_MSG, "删除商品内容数据成功!");
            LOGGER.info("Delete Product Content Info:" + "delete Product Content success!");
        } else {
            context.put(FinalUtil.RETURN_MSG, "删除商品内容数据失败!");
            LOGGER.info("Delete Product Content Info: " + "delete Product Content failure!");
        }
        context.put("pageNo", pageNo);
        context.put(FinalUtil.ENTITYS,contentBodyService.getEntitysByContentId(cid,FinalUtil.SQL_QUERY_SORT_DESC));
        context.put("product", productService.get(pid));
        context.put("content",contentBodyService.get(cid));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.PRODUCT_CON_BODY,context);
    }
}
