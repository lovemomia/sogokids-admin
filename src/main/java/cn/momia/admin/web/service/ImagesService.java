package cn.momia.admin.web.service;

import cn.momia.admin.web.entity.Images;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hoze on 15/6/18.
 */
public interface ImagesService {
    public Images uploadImgs(HttpServletRequest req,int intx,String imgname) ;
    public boolean delimg(HttpServletRequest req, String url);
    public Images uploadImg(HttpServletRequest req,int intx,String imgname);
}
