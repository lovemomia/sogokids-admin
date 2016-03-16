package com.sogokids.images.service;



import com.sogokids.images.model.Images;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/6/18.
 */
public interface ImagesService {
    public Images uploadImgs(HttpServletRequest req, int intx, String imgname) ;
    public boolean delimg(HttpServletRequest req, String url);
    public Images uploadImg(HttpServletRequest req, int intx, String imgname, String isCut);
    public List<Images> uploadMoreImg(HttpServletRequest req,int isName,String imgName,String isCut);
}
