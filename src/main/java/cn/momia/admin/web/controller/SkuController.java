package cn.momia.admin.web.controller;

import cn.momia.admin.web.common.EnumUtil;
import cn.momia.admin.web.common.FileUtil;
import cn.momia.admin.web.common.FinalUtil;
import cn.momia.admin.web.common.PageTypeUtil;
import cn.momia.admin.web.service.AdminUserService;
import cn.momia.admin.web.service.ProductService;
import cn.momia.admin.web.service.QueryPageService;
import cn.momia.admin.web.service.SkuPropertyService;
import cn.momia.admin.web.service.SkuPropertyValueService;
import cn.momia.admin.web.service.SkuService;
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
@RequestMapping("/sku")
public class SkuController {

    private Logger LOGGER = LoggerFactory.getLogger(SkuController.class);

    @Autowired
    private SkuService skuService;

//    @Autowired
//    private SkuPropertyService skuPropertyService;
//
//    @Autowired
//    private SkuPropertyValueService skuPropertyValueService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private QueryPageService queryPageService;

    @RequestMapping("/info")
    public ModelAndView info(@RequestParam("uid") int uid,@RequestParam("pageNo") int pageNo, HttpServletRequest req){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_5, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.SKU,context);
    }

    @RequestMapping("/oper")
    public ModelAndView operation(@RequestParam("uid") int uid,@RequestParam("id") int id, @RequestParam("mark") int mark,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        String reStr = FileUtil.SKU_EDIT;
        Map<String, Object> context = new HashMap<String, Object>();
        if (mark == 0){
            reStr = FileUtil.SKU_ADD;
            context.put(FinalUtil.ENTITYS,productService.getEntitys());
        }else if (mark == 1){
            reStr = FileUtil.SKU_PRICE;
            context.put(FinalUtil.ENTITY,skuService.get(id));
        }else{
            context.put(FinalUtil.ENTITY, skuService.get(id));
            context.put("prices", skuService.getPricesMap(skuService.get(id).getPrices()));
            context.put("properties", skuService.getProperties(skuService.get(id).getProperties()));
            context.put(FinalUtil.ENTITYS, productService.getEntitys());
        }
        context.put("types", EnumUtil.getEnums(FinalUtil.TYPE_1));
        context.put("needs", EnumUtil.getEnums(FinalUtil.TYPE_2));
        context.put("pageNo",pageNo);
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(reStr,context);
    }

    @RequestMapping("/add")
    public ModelAndView addEntity(@RequestParam("uid") int uid,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = skuService.insert(skuService.formEntity(req,FinalUtil.ADD_INFO));
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"添加数据成功!");
            LOGGER.info("Add Sku Info:"+"success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"添加数据失败!");
            LOGGER.info("Add Sku Info:"+"failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_5, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.SKU,context);
    }

    @RequestMapping("/edit")
    public ModelAndView editEntity(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = skuService.update(skuService.formEntity(req, id));
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"修改数据成功!");
            LOGGER.info("Edit Sku Info:" + "success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"修改数据失败!");
            LOGGER.info("Edit Sku Info:" + "failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_5, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.SKU,context);
    }

    @RequestMapping("/del")
    public ModelAndView delEntity(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = skuService.delete(id);
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"删除数据成功!");
            LOGGER.info("Delete Sku Info:" + "success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"删除数据失败!");
            LOGGER.info("Delete Sku Info:" + "failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_5, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.SKU,context);
    }

    @RequestMapping("/price")
    public ModelAndView price(@RequestParam("uid") int uid,@RequestParam("id") int id,@RequestParam("pageNo") int pageNo, HttpServletRequest req){

        Map<String, Object> context = new HashMap<String, Object>();
        int reDate = skuService.update_price(skuService.formEntity2(req, id));
        if (reDate > 0){
            context.put(FinalUtil.RETURN_MSG,"添加价格数据成功!");
            LOGGER.info("Add Sku Price Info:"+"success!");
        }else{
            context.put(FinalUtil.RETURN_MSG,"添加价格数据失败!");
            LOGGER.info("Add Sku Price Info:"+"failure!");
        }
        context.put(FinalUtil.QUERY_PAGE, queryPageService.getEntitys(queryPageService.formEntity(PageTypeUtil.PAGE_TYPE_5, pageNo)));
        context.put(FinalUtil.USER_ENTITY,adminUserService.get(uid));
        return new ModelAndView(FileUtil.SKU,context);
    }
}
